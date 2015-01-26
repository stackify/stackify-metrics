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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackify.api.common.ApiConfiguration;
import com.stackify.api.common.http.HttpClient;
import com.stackify.api.common.http.HttpException;
import com.stackify.api.common.util.Preconditions;

/**
 * MetricSender
 * @author Eric Martin
 */
public class MetricSender {

	/**
	 * The service logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MetricSender.class);

	/**
	 * API configuration
	 */
	private final ApiConfiguration apiConfig;
	
	/**
	 * JSON object mapper
	 */
	private final ObjectMapper objectMapper;

	/**
	 * Monitor service
	 */
	private final MetricMonitorService monitorService;
	
	/**
	 * Constructor
	 * @param apiConfig API configuration
	 * @param objectMapper JSON object mapper
	 * @param monitorService Monitor service
	 */
	public MetricSender(final ApiConfiguration apiConfig, final ObjectMapper objectMapper, final MetricMonitorService monitorService) {
		Preconditions.checkNotNull(apiConfig);
		Preconditions.checkNotNull(objectMapper);
		Preconditions.checkNotNull(monitorService);
		this.apiConfig = apiConfig;
		this.objectMapper = objectMapper;
		this.monitorService = monitorService;
	}
	
	/**
	 * Sends aggregate metrics to Stackify
	 * @param aggregates The aggregate metrics
	 * @throws IOException 
	 * @throws HttpException
	 */
	public void send(final List<MetricAggregate> aggregates) throws IOException, HttpException {
		
		// build the json objects
		
		List<JsonMetric> metrics = new ArrayList<JsonMetric>(aggregates.size());
		
		for (MetricAggregate aggregate : aggregates) {
			
			Integer monitorId = monitorService.getMonitorId(aggregate.getIdentity());
			
			if (monitorId != null) {
				JsonMetric.Builder builder = JsonMetric.newBuilder();
				builder.monitorId(monitorId);
				builder.value(Double.valueOf(aggregate.getValue()));
				builder.count(Integer.valueOf(aggregate.getCount()));
				builder.occurredUtc(new Date(aggregate.getOccurredMillis()));
				builder.monitorTypeId(Integer.valueOf(aggregate.getIdentity().getType().getId()));
				
				metrics.add(builder.build());
			} else {
				LOGGER.info("Unable to determine monitor id for aggregate metric {}", aggregate);
			}
		}
		
		if (metrics.isEmpty()) {
			return;
		}
		
		// convert to json bytes
				
		byte[] jsonBytes = objectMapper.writer().writeValueAsBytes(metrics);
		
		// post to stackify
		
		HttpClient httpClient = new HttpClient(apiConfig);
		httpClient.post("/Metrics/SubmitMetricsByID", jsonBytes);
	}
}
