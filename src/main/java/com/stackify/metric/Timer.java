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

import java.util.Date;

import com.stackify.metric.impl.AbstractMetric;
import com.stackify.metric.impl.MetricMonitorType;

/**
 * Timer
 * @author Eric Martin
 */
public class Timer extends AbstractMetric {

	/**
	 * Constructor
	 * @param category Metric category
	 * @param name Metric name
	 */
	Timer(final String category, final String name) {
		super(category, name, MetricMonitorType.TIMER);
	}
	
	/**
	 * Calculate time taken for operation
	 * @param start Start time of operation
	 */
	public void start(final Date start) {
		if (start != null) {
			submit((System.currentTimeMillis() - start.getTime()) / 1000.0, true);
		}
	}
	
	/**
	 * Calculate time taken for operation
	 * @param start Start time of operation (Unix epoch milliseconds)
	 */
	public void startMs(final long start) {
		submit((System.currentTimeMillis() - start) / 1000.0, true);
	}
	
	/**
	 * Calculate time taken for operation
	 * @param duration Duration (milliseconds)
	 */
	public void durationMs(final long duration) {
		submit(duration / 1000.0, true);
	}

	/**
	 * Auto reports zero if the metric hasn't been modified
	 * @return The current metric instance
	 */
	public Timer autoReportZeroValue() {
		super.autoReportZero();
		return this;
	}
}
