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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.stackify.metric.impl.AbstractMetric;
import com.stackify.metric.impl.Metric;
import com.stackify.metric.impl.MetricCollector;
import com.stackify.metric.impl.MetricIdentity;
import com.stackify.metric.impl.MetricMonitorType;

/**
 * Counter JUnit Test
 * @author Eric Martin
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AbstractMetric.class, MetricManager.class})
public class CounterTest {
	
	/**
	 * testConstructor
	 */
	@Test
	public void testConstructor() {
		String category = "category";
		String name = "name";
		Counter metric = new Counter(category, name);
		
		Assert.assertEquals(category, metric.getIdentity().getCategory());
		Assert.assertEquals(name, metric.getIdentity().getName());
		Assert.assertEquals(MetricMonitorType.COUNTER, metric.getIdentity().getType());
	}
	
	/**
	 * testIncrement
	 */
	@Test
	public void testIncrement() {
		MetricCollector collector = Mockito.mock(MetricCollector.class);
		PowerMockito.mockStatic(MetricManager.class);
		PowerMockito.when(MetricManager.getCollector()).thenReturn(collector);

		Counter metric = new Counter("category", "name");
		
		metric.increment();
				
		ArgumentCaptor<Metric> metricCaptor = ArgumentCaptor.forClass(Metric.class);
		Mockito.verify(collector).submit(metricCaptor.capture());
				
		Assert.assertEquals(metric.getIdentity(), metricCaptor.getValue().getIdentity());
		Assert.assertEquals(1.0, metricCaptor.getValue().getValue(), 0.0001);
		Assert.assertEquals(true, metricCaptor.getValue().isIncrement());
	}
	
	/**
	 * testIncrementValue
	 */
	@Test
	public void testIncrementValue() {
		MetricCollector collector = Mockito.mock(MetricCollector.class);
		PowerMockito.mockStatic(MetricManager.class);
		PowerMockito.when(MetricManager.getCollector()).thenReturn(collector);

		Counter metric = new Counter("category", "name");
		
		long value = 14;

		metric.increment(value);
				
		ArgumentCaptor<Metric> metricCaptor = ArgumentCaptor.forClass(Metric.class);
		Mockito.verify(collector).submit(metricCaptor.capture());
				
		Assert.assertEquals(metric.getIdentity(), metricCaptor.getValue().getIdentity());
		Assert.assertEquals(value, metricCaptor.getValue().getValue(), 0.0001);
		Assert.assertEquals(true, metricCaptor.getValue().isIncrement());
	}
	
	/**
	 * testDecrement
	 */
	@Test
	public void testDecrement() {
		MetricCollector collector = Mockito.mock(MetricCollector.class);
		PowerMockito.mockStatic(MetricManager.class);
		PowerMockito.when(MetricManager.getCollector()).thenReturn(collector);

		Counter metric = new Counter("category", "name");
		
		metric.decrement();
				
		ArgumentCaptor<Metric> metricCaptor = ArgumentCaptor.forClass(Metric.class);
		Mockito.verify(collector).submit(metricCaptor.capture());
				
		Assert.assertEquals(metric.getIdentity(), metricCaptor.getValue().getIdentity());
		Assert.assertEquals(-1.0, metricCaptor.getValue().getValue(), 0.0001);
		Assert.assertEquals(true, metricCaptor.getValue().isIncrement());
	}
	
	/**
	 * testDecrementValue
	 */
	@Test
	public void testDecrementValue() {
		MetricCollector collector = Mockito.mock(MetricCollector.class);
		PowerMockito.mockStatic(MetricManager.class);
		PowerMockito.when(MetricManager.getCollector()).thenReturn(collector);

		Counter metric = new Counter("category", "name");
		
		long value = 14;

		metric.decrement(value);
				
		ArgumentCaptor<Metric> metricCaptor = ArgumentCaptor.forClass(Metric.class);
		Mockito.verify(collector).submit(metricCaptor.capture());
				
		Assert.assertEquals(metric.getIdentity(), metricCaptor.getValue().getIdentity());
		Assert.assertEquals(-value, metricCaptor.getValue().getValue(), 0.0001);
		Assert.assertEquals(true, metricCaptor.getValue().isIncrement());
	}
	
	/**
	 * testAdd
	 */
	@Test
	public void testAdd() {
		MetricCollector collector = Mockito.mock(MetricCollector.class);
		PowerMockito.mockStatic(MetricManager.class);
		PowerMockito.when(MetricManager.getCollector()).thenReturn(collector);

		Counter metric = new Counter("category", "name");
		
		double value = 14.0;

		metric.add(value);
				
		ArgumentCaptor<Metric> metricCaptor = ArgumentCaptor.forClass(Metric.class);
		Mockito.verify(collector).submit(metricCaptor.capture());
				
		Assert.assertEquals(metric.getIdentity(), metricCaptor.getValue().getIdentity());
		Assert.assertEquals(value, metricCaptor.getValue().getValue(), 0.0001);
		Assert.assertEquals(true, metricCaptor.getValue().isIncrement());
	}
	
	/**
	 * testSubtract
	 */
	@Test
	public void testSubtract() {
		MetricCollector collector = Mockito.mock(MetricCollector.class);
		PowerMockito.mockStatic(MetricManager.class);
		PowerMockito.when(MetricManager.getCollector()).thenReturn(collector);

		Counter metric = new Counter("category", "name");
		
		double value = 14.0;

		metric.subtract(value);
				
		ArgumentCaptor<Metric> metricCaptor = ArgumentCaptor.forClass(Metric.class);
		Mockito.verify(collector).submit(metricCaptor.capture());
				
		Assert.assertEquals(metric.getIdentity(), metricCaptor.getValue().getIdentity());
		Assert.assertEquals(-value, metricCaptor.getValue().getValue(), 0.0001);
		Assert.assertEquals(true, metricCaptor.getValue().isIncrement());
	}
	
	/**
	 * testAutoReportZeroValue
	 */
	@Test
	public void testAutoReportZeroValue() {
		MetricCollector collector = Mockito.mock(MetricCollector.class);
		PowerMockito.mockStatic(MetricManager.class);
		PowerMockito.when(MetricManager.getCollector()).thenReturn(collector);

		Counter metric = new Counter("category", "name");
		
		metric.autoReportZeroValue();
				
		ArgumentCaptor<MetricIdentity> metricIdentityCaptor = ArgumentCaptor.forClass(MetricIdentity.class);
		Mockito.verify(collector).autoReportZero(metricIdentityCaptor.capture());
				
		Assert.assertEquals(metric.getIdentity(), metricIdentityCaptor.getValue());
	}
}
