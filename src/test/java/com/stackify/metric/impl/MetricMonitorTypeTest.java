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

/**
 * MetricMonitorType JUnit Test
 * @author Eric Martin
 */
public class MetricMonitorTypeTest {
	
	/**
	 * testIds
	 */
	@Test
	public void testIds() {
		Assert.assertEquals(129, MetricMonitorType.COUNTER.getId());
		Assert.assertEquals(131, MetricMonitorType.TIMER.getId());
		Assert.assertEquals(132, MetricMonitorType.AVERAGE.getId());
		Assert.assertEquals(134, MetricMonitorType.GAUGE.getId());
	}
}
