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
 * Metric JUnit Test
 * @author Eric Martin
 */
public class MetricTest {

	/**
	 * testBuilder
	 */
	@Test
	public void testBuilder() {
		MetricIdentity identity = Mockito.mock(MetricIdentity.class);
		double value = 14;
		boolean isIncrement = true;

		Metric.Builder builder = Metric.newBuilder();
		builder.identity(identity);
		builder.value(value);
		builder.isIncrement(isIncrement);
		
		long beginTimer = System.currentTimeMillis();

		Metric metric = builder.build();
		
		long endTimer = System.currentTimeMillis();

		Assert.assertNotNull(metric);
		Assert.assertEquals(identity, metric.getIdentity());
		Assert.assertEquals(value, metric.getValue(), 0.0001);
		Assert.assertEquals(isIncrement, metric.isIncrement());
		Assert.assertTrue(beginTimer <= metric.getOccurredMillis());
		Assert.assertTrue(metric.getOccurredMillis() <= endTimer);
		
		Assert.assertNotNull(metric.toString());
	}
}
