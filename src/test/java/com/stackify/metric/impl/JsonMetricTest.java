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

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * JsonMetric JUnit Test
 * @author Eric Martin
 */
public class JsonMetricTest {

	/**
	 * testBuilder
	 */
	@Test
	public void testBuilder() {
		Integer monitorId = 14;
		Double value = 15.5;
		Integer count = 23;
		Date occurredUtc = new Date();
		Integer monitorTypeId = 34;
		
		JsonMetric.Builder builder = JsonMetric.newBuilder();
		builder.monitorId(monitorId);
		builder.value(value);
		builder.count(count);
		builder.occurredUtc(occurredUtc);
		builder.monitorTypeId(monitorTypeId);
		
		JsonMetric metric = builder.build();
		
		Assert.assertNotNull(metric);
		Assert.assertEquals(monitorId, metric.getMonitorId());
		Assert.assertEquals(value, metric.getValue());
		Assert.assertEquals(count, metric.getCount());
		Assert.assertEquals(occurredUtc, metric.getOccurredUtc());
		Assert.assertEquals(monitorTypeId, metric.getMonitorTypeId());		
	}
}
