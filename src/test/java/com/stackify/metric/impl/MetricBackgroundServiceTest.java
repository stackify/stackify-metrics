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

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.stackify.api.common.http.HttpException;

/**
 * MetricBackgroundService JUnit Test
 * @author Eric Martin
 */
public class MetricBackgroundServiceTest {

	/**
	 * testConstructor
	 */
	@Test
	public void testConstructor() {
		MetricCollector collector = Mockito.mock(MetricCollector.class);
		MetricSender sender = Mockito.mock(MetricSender.class);
		MetricBackgroundService service = new MetricBackgroundService(collector, sender);
		
		Assert.assertNotNull(service.scheduler());
	}
		
	/**
	 * testShutDown
	 * @throws Exception 
	 */
	@Test
	public void testShutDown() throws Exception {
		MetricCollector collector = Mockito.mock(MetricCollector.class);
		MetricSender sender = Mockito.mock(MetricSender.class);
		MetricBackgroundService service = new MetricBackgroundService(collector, sender);
		
		service.shutDown();
		
		Mockito.verify(collector).flush(Mockito.any(MetricSender.class));
	}
	
	/**
	 * testRunOneIteration
	 * @throws HttpException 
	 * @throws IOException 
	 */
	@Test
	public void testRunOneIteration() throws IOException, HttpException {
		MetricCollector collector = Mockito.mock(MetricCollector.class);
		MetricSender sender = Mockito.mock(MetricSender.class);
		MetricBackgroundService service = new MetricBackgroundService(collector, sender);
				
		service.runOneIteration();
		
		Mockito.verify(collector).flush(Mockito.any(MetricSender.class));
	}
}
