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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.stackify.api.AppIdentity;
import com.stackify.api.common.ApiConfiguration;
import com.stackify.api.common.AppIdentityService;
import com.stackify.api.common.http.HttpClient;

/**
 * MetricMonitorServiceTest
 * @author Eric Martin
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MetricMonitorService.class, HttpClient.class})
public class MetricMonitorServiceTest {

	/**
	 * testNoAppIdentity
	 * @throws Exception 
	 */
	@Test
	public void testNoAppIdentity() throws Exception {
		ApiConfiguration apiConfig = Mockito.mock(ApiConfiguration.class);
		ObjectMapper objectMapper = new ObjectMapper();
		AppIdentityService appIdentityService = Mockito.mock(AppIdentityService.class);
		Mockito.when(appIdentityService.getAppIdentity()).thenReturn(Optional.<AppIdentity>absent());

		HttpClient httpClient = PowerMockito.mock(HttpClient.class);
		PowerMockito.whenNew(HttpClient.class).withAnyArguments().thenReturn(httpClient);
		PowerMockito.when(httpClient.post(Mockito.anyString(), (byte[]) Mockito.any())).thenReturn("");

		MetricMonitorService service = new MetricMonitorService(apiConfig, objectMapper, appIdentityService);

		MetricIdentity identity = new MetricIdentity("category", "name", MetricMonitorType.COUNTER);
		
		Optional<Integer> id = service.getMonitorId(identity);

		Assert.assertNotNull(id);
		Assert.assertFalse(id.isPresent());
		
		Mockito.verifyZeroInteractions(httpClient);
	}
	
	/**
	 * testGetMonitorId
	 * @throws Exception 
	 */
	@Test
	public void testGetMonitorId() throws Exception {
		ApiConfiguration apiConfig = Mockito.mock(ApiConfiguration.class);
		ObjectMapper objectMapper = new ObjectMapper();
		AppIdentityService appIdentityService = Mockito.mock(AppIdentityService.class);
		Mockito.when(appIdentityService.getAppIdentity()).thenReturn(Optional.of(Mockito.mock(AppIdentity.class)));

		HttpClient httpClient = PowerMockito.mock(HttpClient.class);
		PowerMockito.whenNew(HttpClient.class).withAnyArguments().thenReturn(httpClient);
		PowerMockito.when(httpClient.post(Mockito.anyString(), (byte[]) Mockito.any())).thenReturn("{\"MonitorID\": \"14\"}").thenReturn("");

		MetricMonitorService service = new MetricMonitorService(apiConfig, objectMapper, appIdentityService);

		MetricIdentity identity = new MetricIdentity("category", "name", MetricMonitorType.COUNTER);
		
		Optional<Integer> id = service.getMonitorId(identity);

		Assert.assertNotNull(id);
		Assert.assertTrue(id.isPresent());
		Assert.assertEquals(14, id.get().intValue());
		
		Optional<Integer> cachedId = service.getMonitorId(identity);
		
		Assert.assertNotNull(cachedId);
		Assert.assertTrue(cachedId.isPresent());
		Assert.assertEquals(14, cachedId.get().intValue());
		
		Optional<Integer> absentId = service.getMonitorId(new MetricIdentity("does-not", "exist", MetricMonitorType.COUNTER));

		Assert.assertNotNull(absentId);
		Assert.assertFalse(absentId.isPresent());
	}
}
