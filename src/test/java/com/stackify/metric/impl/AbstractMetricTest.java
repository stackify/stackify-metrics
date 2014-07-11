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
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.stackify.metric.MetricManager;

/**
 * AbstractMetric JUnit Test
 * @author Eric Martin
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AbstractMetric.class, MetricManager.class})
public class AbstractMetricTest {

	/**
	 * testConstructor
	 */
	@Test
	public void testConstructor() {
		String category = "category";
		String name = "name";
		MetricMonitorType type = MetricMonitorType.COUNTER;
		
		TestMetric metric = new TestMetric(category, name, type);
		
		Assert.assertNotNull(metric.getIdentity());
		Assert.assertEquals(category, metric.getIdentity().getCategory());
		Assert.assertEquals(name, metric.getIdentity().getName());
		Assert.assertEquals(type, metric.getIdentity().getType());
	}
	
	/**
	 * testSubmit
	 */
	@Test
	public void testSubmit() {
		MetricCollector collector = Mockito.mock(MetricCollector.class);
		PowerMockito.mockStatic(MetricManager.class);
		PowerMockito.when(MetricManager.getCollector()).thenReturn(collector);

		TestMetric metric = new TestMetric("category", "name", MetricMonitorType.COUNTER);

		double value = 14.0;
		boolean isIncrement = false;
		
		metric.submit(value, isIncrement);
		
		ArgumentCaptor<Metric> metricCaptor = ArgumentCaptor.forClass(Metric.class);
		Mockito.verify(collector).submit(metricCaptor.capture());
				
		Assert.assertEquals(metric.getIdentity(), metricCaptor.getValue().getIdentity());
		Assert.assertEquals(value, metricCaptor.getValue().getValue(), 0.0001);
		Assert.assertEquals(isIncrement, metricCaptor.getValue().isIncrement());
	}
	
	/**
	 * testAutoReportZero
	 */
	@Test
	public void testAutoReportZero() {
		MetricCollector collector = Mockito.mock(MetricCollector.class);
		PowerMockito.mockStatic(MetricManager.class);
		PowerMockito.when(MetricManager.getCollector()).thenReturn(collector);

		TestMetric metric = new TestMetric("category", "name", MetricMonitorType.COUNTER);
		
		metric.autoReportZero();
		
		ArgumentCaptor<MetricIdentity> metricIdentityCaptor = ArgumentCaptor.forClass(MetricIdentity.class);
		Mockito.verify(collector).autoReportZero(metricIdentityCaptor.capture());
				
		Assert.assertEquals(metric.getIdentity(), metricIdentityCaptor.getValue());
	}
	
	/**
	 * testAutoReportLast
	 */
	@Test
	public void testAutoReportLast() {
		MetricCollector collector = Mockito.mock(MetricCollector.class);
		PowerMockito.mockStatic(MetricManager.class);
		PowerMockito.when(MetricManager.getCollector()).thenReturn(collector);

		TestMetric metric = new TestMetric("category", "name", MetricMonitorType.COUNTER);
		
		metric.autoReportLast();
		
		ArgumentCaptor<MetricIdentity> metricIdentityCaptor = ArgumentCaptor.forClass(MetricIdentity.class);
		Mockito.verify(collector).autoReportLast(metricIdentityCaptor.capture());
				
		Assert.assertEquals(metric.getIdentity(), metricIdentityCaptor.getValue());
	}
	
	/**
	 * testSubmitWithoutCollector
	 */
	@Test
	public void testSubmitWithoutCollector() {
		PowerMockito.mockStatic(MetricManager.class);
		PowerMockito.when(MetricManager.getCollector()).thenReturn(null);

		TestMetric metric = new TestMetric("category", "name", MetricMonitorType.COUNTER);
		metric.submit(14.0, false);		
	}
	
	/**
	 * testAutoReportZeroWithoutCollector
	 */
	@Test
	public void testAutoReportZeroWithoutCollector() {
		PowerMockito.mockStatic(MetricManager.class);
		PowerMockito.when(MetricManager.getCollector()).thenReturn(null);

		TestMetric metric = new TestMetric("category", "name", MetricMonitorType.COUNTER);
		metric.autoReportZero();
	}
	
	/**
	 * testAutoReportLastWithoutCollector
	 */
	@Test
	public void testAutoReportLastWithoutCollector() {
		PowerMockito.mockStatic(MetricManager.class);
		PowerMockito.when(MetricManager.getCollector()).thenReturn(null);

		TestMetric metric = new TestMetric("category", "name", MetricMonitorType.COUNTER);
		metric.autoReportLast();
	}
	
	/**
	 * TestMetric
	 */
	public static class TestMetric extends AbstractMetric {
		public TestMetric(final String category, final String name, final MetricMonitorType type) {
			super(category, name, type);
		}		
	}
}
