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

import com.stackify.metric.impl.AbstractMetric;
import com.stackify.metric.impl.MetricMonitorType;

/**
 * Gauge
 * @author Eric Martin
 */
public class Gauge extends AbstractMetric {

	/**
	 * Constructor
	 * @param category Metric category
	 * @param name Metric name
	 */
	Gauge(final String category, final String name) {
		super(category, name, MetricMonitorType.GAUGE);
	}

	/**
	 * Sets the value of this gauge
	 * @param value The value
	 */
	public void set(final double value) {
		submit(value, false);
	}
	
	/**
	 * Adds the specified value to this gauge
	 * @param value The value
	 */
	public void add(final double value) {
		submit(value, true);
	}
	
	/**
	 * Subtracts the specified value from this gauge
	 * @param value The value
	 */
	public void subtract(final double value) {
		submit(-value, true);
	}
	
	/**
	 * Auto reports zero if the metric hasn't been modified
	 * @return The current metric instance
	 */
	public Gauge autoReportZeroValue() {
		super.autoReportZero();
		return this;
	}
	
	/**
	 * Auto reports the last value if the metric hasn't been modified
	 * @return The current metric instance
	 */
	public Gauge autoReportLastValue() {
		super.autoReportLast();
		return this;
	}
}
