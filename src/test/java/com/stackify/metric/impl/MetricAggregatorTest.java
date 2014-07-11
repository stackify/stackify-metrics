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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * MetricAggregatorTest
 * @author Eric Martin
 */
public class MetricAggregatorTest {

	/**
	 * testAddCounter
	 */
	@Test
	public void testAddCounter() {
		MetricIdentity identity = new MetricIdentity("category", "name", MetricMonitorType.COUNTER);
		Metric metric = Metric.newBuilder().identity(identity).value(14.0).isIncrement(true).build();
		
		MetricAggregator aggregator = new MetricAggregator(System.currentTimeMillis(), new HashMap<MetricIdentity, Double>());
		
		aggregator.add(metric);
		
		List<MetricAggregate> aggregates = aggregator.getAggregates();

		Assert.assertNotNull(aggregates);
		Assert.assertEquals(1, aggregates.size());
		
		Assert.assertEquals(identity, aggregates.get(0).getIdentity());
		Assert.assertEquals(1, aggregates.get(0).getCount());
		Assert.assertEquals(14.0, aggregates.get(0).getValue(), 0.0001);

		aggregator.add(metric);
		
		List<MetricAggregate> newAggregates = aggregator.getAggregates();

		Assert.assertNotNull(newAggregates);
		Assert.assertEquals(1, newAggregates.size());
		
		Assert.assertEquals(identity, newAggregates.get(0).getIdentity());
		Assert.assertEquals(2, newAggregates.get(0).getCount());
		Assert.assertEquals(28.0, newAggregates.get(0).getValue(), 0.0001);
	}
	
	/**
	 * testAddGauge
	 */
	@Test
	public void testAddGauge() {
		MetricIdentity identity = new MetricIdentity("category", "name", MetricMonitorType.GAUGE);
		Metric metric1 = Metric.newBuilder().identity(identity).value(14.0).isIncrement(false).build();
		Metric metric2 = Metric.newBuilder().identity(identity).value(33.0).isIncrement(true).build();
		
		MetricAggregator aggregator = new MetricAggregator(System.currentTimeMillis(), new HashMap<MetricIdentity, Double>());
		
		aggregator.add(metric1);
		
		List<MetricAggregate> aggregates = aggregator.getAggregates();

		Assert.assertNotNull(aggregates);
		Assert.assertEquals(1, aggregates.size());
		
		Assert.assertEquals(identity, aggregates.get(0).getIdentity());
		Assert.assertEquals(1, aggregates.get(0).getCount());
		Assert.assertEquals(14.0, aggregates.get(0).getValue(), 0.0001);

		aggregator.add(metric2);
		
		List<MetricAggregate> newAggregates = aggregator.getAggregates();

		Assert.assertNotNull(newAggregates);
		Assert.assertEquals(1, newAggregates.size());
		
		Assert.assertEquals(identity, newAggregates.get(0).getIdentity());
		Assert.assertEquals(1, newAggregates.get(0).getCount());
		Assert.assertEquals(47.0, newAggregates.get(0).getValue(), 0.0001);
	}
	
	/**
	 * testAutoReportZero
	 */
	@Test
	public void testAutoReportZero() {
		MetricIdentity identity = new MetricIdentity("category", "name", MetricMonitorType.COUNTER);
		
		Map<MetricIdentity, Double> lastValues = Maps.newHashMap();
		lastValues.put(identity, Double.valueOf(14.0));

		MetricAggregator aggregator = new MetricAggregator(System.currentTimeMillis(), lastValues);
		
		aggregator.autoReportZero(Sets.newHashSet(identity));
		
		List<MetricAggregate> aggregates = aggregator.getAggregates();

		Assert.assertNotNull(aggregates);
		Assert.assertEquals(1, aggregates.size());
		
		Assert.assertEquals(identity, aggregates.get(0).getIdentity());
		Assert.assertEquals(1, aggregates.get(0).getCount());
		Assert.assertEquals(0, aggregates.get(0).getValue(), 0.0001);
	}
	
	/**
	 * autoReportLast
	 */
	@Test
	public void testAutoReportLast() {
		MetricIdentity identity = new MetricIdentity("category", "name", MetricMonitorType.COUNTER);
		
		Map<MetricIdentity, Double> lastValues = Maps.newHashMap();
		lastValues.put(identity, Double.valueOf(14.0));
		
		MetricAggregator aggregator = new MetricAggregator(System.currentTimeMillis(), lastValues);
		
		aggregator.autoReportLast(Sets.newHashSet(identity));
		
		List<MetricAggregate> aggregates = aggregator.getAggregates();

		Assert.assertNotNull(aggregates);
		Assert.assertEquals(1, aggregates.size());
		
		Assert.assertEquals(identity, aggregates.get(0).getIdentity());
		Assert.assertEquals(1, aggregates.get(0).getCount());
		Assert.assertEquals(14.0, aggregates.get(0).getValue(), 0.0001);
	}
	
	/**
	 * testAutoReportLastWithoutLast
	 */
	@Test
	public void testAutoReportLastWithoutLast() {
		MetricIdentity identity = new MetricIdentity("category", "name", MetricMonitorType.COUNTER);
		
		Map<MetricIdentity, Double> lastValues = Maps.newHashMap();
		
		MetricAggregator aggregator = new MetricAggregator(System.currentTimeMillis(), lastValues);
		
		aggregator.autoReportLast(Sets.newHashSet(identity));
		
		List<MetricAggregate> aggregates = aggregator.getAggregates();

		Assert.assertNotNull(aggregates);
		Assert.assertEquals(1, aggregates.size());
		
		Assert.assertEquals(identity, aggregates.get(0).getIdentity());
		Assert.assertEquals(1, aggregates.get(0).getCount());
		Assert.assertEquals(0.0, aggregates.get(0).getValue(), 0.0001);
	}
}
