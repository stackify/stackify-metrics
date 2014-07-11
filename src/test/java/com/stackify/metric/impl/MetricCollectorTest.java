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
 * MetricCollectorTest
 * @author Eric Martin
 */
public class MetricCollectorTest {

	/**
	 * testSubmitAndFlush
	 * @throws HttpException 
	 * @throws IOException 
	 */
	@Test
	public void testFlushEmpty() throws IOException, HttpException {
		MetricSender sender = Mockito.mock(MetricSender.class);
		
		MetricCollector collector = new MetricCollector();		
		int zero = collector.flush(sender);

		Assert.assertEquals(0, zero);
		
		Mockito.verifyZeroInteractions(sender);
	}
	
	/**
	 * testSubmitAndFlush
	 * @throws HttpException 
	 * @throws IOException 
	 */
	@Test
	public void testSubmitAndFlush() throws IOException, HttpException {		
		MetricIdentity identity = new MetricIdentity("category", "name", MetricMonitorType.COUNTER);
		Metric metric = Mockito.mock(Metric.class);
		Mockito.when(metric.getIdentity()).thenReturn(identity);
		Mockito.when(metric.getOccurredMillis()).thenReturn(System.currentTimeMillis() - (5 * 60 * 1000));
		
		MetricSender sender = Mockito.mock(MetricSender.class);
		
		MetricCollector collector = new MetricCollector();
		
		collector.submit(metric);
		
		int one = collector.flush(sender);

		Assert.assertEquals(1, one);
		
		Mockito.verify(sender).send(Mockito.anyList());
	}
}
