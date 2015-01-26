/*
 * Copyright 2014 Stackify
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stackify.metric.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.stackify.api.AppIdentity;
import com.stackify.api.common.ApiConfiguration;
import com.stackify.api.common.AppIdentityService;
import com.stackify.api.common.http.HttpClient;
import com.stackify.api.common.http.HttpException;
import com.stackify.api.common.util.Preconditions;

/**
 * MetricMonitorService
 * @author Eric Martin
 */
public class MetricMonitorService {

	/**
	 * The service logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MetricMonitorService.class);

	/**
	 * Five minutes (in milliseconds)
	 */
	private static long FIVE_MINUTES_MILLIS = 300000;
	
	/**
	 * Timestamp of the last queries
	 */
	private final Map<MetricIdentity, Long> lastQueries = new HashMap<MetricIdentity, Long>();
	
	/**
	 * The cached monitor ids
	 */
	private Map<MetricIdentity, Integer> monitorIds = new HashMap<MetricIdentity, Integer>();
	
	/**
	 * API configuration
	 */
	private final ApiConfiguration apiConfig;
	
	/**
	 * JSON object mapper
	 */
	private final ObjectMapper objectMapper;

	/**
	 * Application identity service
	 */
	public final AppIdentityService appIdentityService;
	
	/**
	 * Constructor
	 * @param apiConfig API configuration
	 * @param objectMapper JSON object mapper
	 * @param appIdentityService Application identity service
	 */
	public MetricMonitorService(final ApiConfiguration apiConfig, final ObjectMapper objectMapper, final AppIdentityService appIdentityService) {
		Preconditions.checkNotNull(apiConfig);
		Preconditions.checkNotNull(objectMapper);
		Preconditions.checkNotNull(appIdentityService);
		this.apiConfig = apiConfig;
		this.objectMapper = objectMapper;
		this.appIdentityService = appIdentityService;
	}
	
	/**
	 * Gets the monitor id for this metric
	 * @param identity The metric identity
	 * @return The monitor id (optional)
	 * @throws IOException
	 * @throws HttpException
	 */
	public Integer getMonitorId(final MetricIdentity identity) throws IOException, HttpException {
		Preconditions.checkNotNull(identity);
		
		if (monitorIds.containsKey(identity)) {
			return monitorIds.get(identity);
		}
		
		long lastQuery = 0;
		
		if (lastQueries.containsKey(identity)) {
			lastQuery = lastQueries.get(identity);
		}
		
		long currentTimeMillis = System.currentTimeMillis();
		
		if (lastQuery + FIVE_MINUTES_MILLIS < currentTimeMillis) {
			try {
				lastQueries.put(identity, lastQuery);
				
				AppIdentity appIdentity = appIdentityService.getAppIdentity();
				
				if (appIdentity != null) {
					
					int monitorId = getMetricInfo(identity, appIdentity);
					
					LOGGER.debug("Metric {} monitor id: {}", identity, monitorId);
					
					if (0 < monitorId) {
						monitorIds.put(identity, monitorId);
					}
				}
			} catch (Throwable t) {
				LOGGER.info("Unable to determine monitor id for metric {}", identity, t);
			}
		}

		if (monitorIds.containsKey(identity)) {
			return monitorIds.get(identity);
		}
		
		return null;
	}

	/**
	 * Gets the monitor id for this metric
	 * @param identity The metric identity
	 * @return The monitor id (optional)
	 * @throws IOException
	 * @throws HttpException
	 */
	private int getMetricInfo(final MetricIdentity identity, final AppIdentity appIdentity) throws IOException, HttpException {
		Preconditions.checkNotNull(identity);
		Preconditions.checkNotNull(appIdentity);
		
		// build the json objects
		
		JsonGetMetricInfoRequest.Builder requestBuilder = JsonGetMetricInfoRequest.newBuilder();
		requestBuilder.category(identity.getCategory());
		requestBuilder.metricName(identity.getName());
		requestBuilder.deviceId(appIdentity.getDeviceId());
		requestBuilder.deviceAppId(appIdentity.getDeviceAppId());
		requestBuilder.appNameId(appIdentity.getAppNameId());
		requestBuilder.metricTypeId(identity.getType().getId());
		
		JsonGetMetricInfoRequest request = requestBuilder.build();
		
		// convert to json bytes
				
		byte[] jsonBytes = objectMapper.writer().writeValueAsBytes(request);
		
		// post to stackify
		
		HttpClient httpClient = new HttpClient(apiConfig);
		String responseString = httpClient.post("/Metrics/GetMetricInfo", jsonBytes);
		
		// deserialize the response and return the monitor id
		
		ObjectReader jsonReader = objectMapper.reader(new TypeReference<JsonGetMetricInfoResponse>(){});
		JsonGetMetricInfoResponse response = jsonReader.readValue(responseString);
		
		if (response != null) {
			return response.getMonitorId();
		}
		
		return 0;
	}
}
