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

/**
 * MetricFactory JUnit Test
 * @author Eric Martin
 */
public class MetricFactoryTest {

	/**
	 * testGetGauge
	 */
	@Test
	public void testGetGauge() {
		String category = "category";
		String name = "name";
		
		Gauge gauge = MetricFactory.getGauge(category, name);

		Assert.assertNotNull(gauge);
		Assert.assertEquals(category, gauge.getIdentity().getCategory());
		Assert.assertEquals(name, gauge.getIdentity().getName());
	}

	/**
	 * testGetCounter
	 */
	@Test
	public void testGetCounter() {
		String category = "category";
		String name = "name";
		
		Counter counter = MetricFactory.getCounter(category, name);

		Assert.assertNotNull(counter);
		Assert.assertEquals(category, counter.getIdentity().getCategory());
		Assert.assertEquals(name, counter.getIdentity().getName());
	}

	/**
	 * testGetTimer
	 */
	@Test
	public void testGetTimer() {
		String category = "category";
		String name = "name";
		
		Timer timer = MetricFactory.getTimer(category, name);

		Assert.assertNotNull(timer);
		Assert.assertEquals(category, timer.getIdentity().getCategory());
		Assert.assertEquals(name, timer.getIdentity().getName());
	}

	/**
	 * testGetAverage
	 */
	@Test
	public void testGetAverage() {
		String category = "category";
		String name = "name";
		
		Average average = MetricFactory.getAverage(category, name);

		Assert.assertNotNull(average);
		Assert.assertEquals(category, average.getIdentity().getCategory());
		Assert.assertEquals(name, average.getIdentity().getName());
	}

	/**
	 * testGetCounterAndTimer
	 */
	@Test
	public void testGetCounterAndTimer() {
		String category = "category";
		String name = "name";
		
		CounterAndTimer ct = MetricFactory.getCounterAndTimer(category, name);

		Assert.assertNotNull(ct);
		Assert.assertNotNull(ct.getCounter());
		Assert.assertNotNull(ct.getTimer());
	}
}
