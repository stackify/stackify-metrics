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
package com.stackify.metric;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackify.api.common.ApiConfiguration;
import com.stackify.api.common.ApiConfigurations;
import com.stackify.api.common.AppIdentityService;
import com.stackify.api.common.EnvironmentDetails;
import com.stackify.metric.impl.MetricBackgroundService;
import com.stackify.metric.impl.MetricCollector;
import com.stackify.metric.impl.MetricMonitorService;
import com.stackify.metric.impl.MetricSender;

/**
 * MetricManager
 * @author Eric Martin
 */
public class MetricManager {
	
	/**
	 * Logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MetricManager.class);

	/**
	 * True if the Metrics API has been initialized, false otherwise
	 */
	private static AtomicBoolean INITIALIZED = new AtomicBoolean(false);
	
	/**
	 * API configuration
	 */
	private static ApiConfiguration CONFIG = null;

	/**
	 * Metric collector
	 */
	private static MetricCollector COLLECTOR = new MetricCollector();
	
	/**
	 * Background service for sending metrics to Stackify
	 */
	private static MetricBackgroundService BACKGROUND_SERVICE = null;
				
	/**
	 * @return The metric collector
	 */
	public static MetricCollector getCollector() {
		if (INITIALIZED.compareAndSet(false, true)) {
			startup();
		}
		
		return COLLECTOR;
	}
	
	/**
	 * Manually configure the metrics api
	 * @param config API configuration
	 */
	public static synchronized void configure(final ApiConfiguration config) {
		
		ApiConfiguration.Builder builder = ApiConfiguration.newBuilder();
		
		builder.apiUrl(config.getApiUrl());
		builder.apiKey(config.getApiKey());
		builder.application(config.getApplication());
		builder.environment(config.getEnvironment());
		
		if (config.getEnvDetail() == null)
		{
			builder.envDetail(EnvironmentDetails.getEnvironmentDetail(config.getApplication(), config.getEnvironment()));
		}
		else
		{
			builder.envDetail(config.getEnvDetail());
		}
		
		CONFIG = builder.build();
	}
	
	/**
	 * Start up the background thread that is processing metrics
	 */
	private static synchronized void startup() {
		
		try {
			if (CONFIG == null) {
				CONFIG = ApiConfigurations.fromProperties();
			}
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			AppIdentityService appIdentityService = new AppIdentityService(CONFIG, objectMapper, true);

			MetricMonitorService monitorService = new MetricMonitorService(CONFIG, objectMapper, appIdentityService);
			
			MetricSender sender = new MetricSender(CONFIG, objectMapper, monitorService);
			
			BACKGROUND_SERVICE = new MetricBackgroundService(COLLECTOR, sender);
			BACKGROUND_SERVICE.start();
			
		} catch (Throwable t) {
			LOGGER.error("Exception starting Stackify Metrics API service", t);
		}
	}
	
	/**
	 * Shut down the background thread that is processing metrics
	 */
	public static synchronized void shutdown() {
		if (BACKGROUND_SERVICE != null) {
			try {
				BACKGROUND_SERVICE.stop();
			} catch (Throwable t) {
				LOGGER.error("Exception stopping Stackify Metrics API service", t);
			}
			
			INITIALIZED.compareAndSet(true, false);
		}
	}
	
	/**
	 * Hidden to prevent construction
	 */
	private MetricManager() {
	}
}
