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

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.stackify.api.common.ApiConfiguration;
import com.stackify.api.common.http.HttpClient;

/**
 * MetricSenderTest
 * @author Eric Martin
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MetricSender.class, HttpClient.class})
public class MetricSenderTest {

	/**
	 * testSend
	 * @throws Exception 
	 */
	@Test
	public void testSend() throws Exception {
		ApiConfiguration apiConfig = Mockito.mock(ApiConfiguration.class);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		MetricMonitorService monitorService = Mockito.mock(MetricMonitorService.class);
		Mockito.when(monitorService.getMonitorId(Mockito.any(MetricIdentity.class))).thenReturn(Optional.of(14));
		
		HttpClient httpClient = PowerMockito.mock(HttpClient.class);
		PowerMockito.whenNew(HttpClient.class).withAnyArguments().thenReturn(httpClient);
		PowerMockito.when(httpClient.post(Mockito.anyString(), (byte[]) Mockito.any())).thenReturn("");
		
		MetricSender sender = new MetricSender(apiConfig, objectMapper, monitorService);
		
		MetricIdentity identity = new MetricIdentity("category", "name", MetricMonitorType.GAUGE);		
		MetricAggregate aggregate = MetricAggregate.fromMetricIdentity(identity, System.currentTimeMillis());
		
		sender.send(Collections.singletonList(aggregate));
		
		Mockito.verify(httpClient).post(Mockito.anyString(), Mockito.any(byte[].class));
	}
	
	/**
	 * testSendWithoutMonitorId
	 * @throws Exception 
	 */
	@Test
	public void testSendWithoutMonitorId() throws Exception {
		ApiConfiguration apiConfig = Mockito.mock(ApiConfiguration.class);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		MetricMonitorService monitorService = Mockito.mock(MetricMonitorService.class);
		Mockito.when(monitorService.getMonitorId(Mockito.any(MetricIdentity.class))).thenReturn(Optional.<Integer>absent());
		
		HttpClient httpClient = PowerMockito.mock(HttpClient.class);
		PowerMockito.whenNew(HttpClient.class).withAnyArguments().thenReturn(httpClient);
		
		MetricSender sender = new MetricSender(apiConfig, objectMapper, monitorService);
		
		MetricIdentity identity = new MetricIdentity("category", "name", MetricMonitorType.GAUGE);		
		MetricAggregate aggregate = MetricAggregate.fromMetricIdentity(identity, System.currentTimeMillis());
		
		sender.send(Collections.singletonList(aggregate));
		
		Mockito.verifyZeroInteractions(httpClient);
	}
}
