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

import java.net.HttpURLConnection;

import org.junit.Assert;
import org.junit.Test;

import com.stackify.api.common.http.HttpException;

/**
 * MetricBackgroundServiceScheduler JUnit Test
 * 
 * @author Eric Martin
 */
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({MetricBackgroundServiceScheduler.class, System.class})
public class MetricBackgroundServiceSchedulerTest {

	/**
	 * testUpdateOk
	 */
	@Test
	public void testNoUpdate() {
		MetricBackgroundServiceScheduler scheduler = new MetricBackgroundServiceScheduler();
		
		Assert.assertEquals(5000, scheduler.getScheduleDelay());
		Assert.assertNotNull(scheduler.getNextSchedule());
	}
	
	/**
	 * testUpdateOk
	 */
	@Test
	public void testUpdateOk() {
		MetricBackgroundServiceScheduler scheduler = new MetricBackgroundServiceScheduler();

		scheduler.update(50);
		
		Assert.assertEquals(5000, scheduler.getScheduleDelay());
		Assert.assertNotNull(scheduler.getNextSchedule());
	}
	
	/**
	 * testUpdateUnauthorized
	 */
	@Test
	public void testUpdateUnauthorized() {
		MetricBackgroundServiceScheduler scheduler = new MetricBackgroundServiceScheduler();
		
		scheduler.update(new HttpException(HttpURLConnection.HTTP_UNAUTHORIZED));

		Assert.assertEquals(300000, scheduler.getScheduleDelay());
		Assert.assertNotNull(scheduler.getNextSchedule());
	}
		
	/**
	 * testUpdateError
	 */
	@Test
	public void testUpdateError() {
		MetricBackgroundServiceScheduler scheduler = new MetricBackgroundServiceScheduler();
		
		scheduler.update(new HttpException(HttpURLConnection.HTTP_INTERNAL_ERROR));

		Assert.assertEquals(15000, scheduler.getScheduleDelay());
		Assert.assertNotNull(scheduler.getNextSchedule());
	}

	/**
	 * testUpdateErrorAndClear
	 */
	@Test
	public void testUpdateErrorAndClear() {
		MetricBackgroundServiceScheduler scheduler = new MetricBackgroundServiceScheduler();
		
		scheduler.update(new HttpException(HttpURLConnection.HTTP_INTERNAL_ERROR));

		Assert.assertEquals(15000, scheduler.getScheduleDelay());
		Assert.assertNotNull(scheduler.getNextSchedule());

		scheduler.update(50);
		
		Assert.assertEquals(5000, scheduler.getScheduleDelay());
		Assert.assertNotNull(scheduler.getNextSchedule());
	}
}