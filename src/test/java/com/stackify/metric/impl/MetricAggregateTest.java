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
import org.mockito.Mockito;

/**
 * MetricAggregate JUnit Test
 * @author Eric Martin
 */
public class MetricAggregateTest {
	
	/**
	 * testFromMetricIdentity
	 */
	@Test
	public void testFromMetricIdentity() {
		
		MetricIdentity identity = Mockito.mock(MetricIdentity.class);
		long time = System.currentTimeMillis();
		
		MetricAggregate aggregate = MetricAggregate.fromMetricIdentity(identity, time);
		
		Assert.assertNotNull(aggregate);
		Assert.assertEquals(identity, aggregate.getIdentity());
		Assert.assertEquals(time, aggregate.getOccurredMillis());
		Assert.assertEquals(0.0, aggregate.getValue(), 0.0001);
		Assert.assertEquals(0, aggregate.getCount());
	}
	
	/**
	 * testGetSetCount
	 */
	@Test
	public void testGetSetCount() {
		MetricIdentity identity = Mockito.mock(MetricIdentity.class);
		MetricAggregate aggregate = MetricAggregate.fromMetricIdentity(identity, System.currentTimeMillis());
		
		Assert.assertEquals(0, aggregate.getCount());

		int count = 14;
		aggregate.setCount(count);
		
		Assert.assertEquals(count, aggregate.getCount());
	}
	
	/**
	 * testGetSetValue
	 */
	@Test
	public void testGetSetValue() {
		MetricIdentity identity = Mockito.mock(MetricIdentity.class);
		MetricAggregate aggregate = MetricAggregate.fromMetricIdentity(identity, System.currentTimeMillis());
		
		Assert.assertEquals(0, aggregate.getCount());

		double value = 14.0;
		aggregate.setValue(value);
		
		Assert.assertEquals(value, aggregate.getValue(), 0.0001);
	}
}
