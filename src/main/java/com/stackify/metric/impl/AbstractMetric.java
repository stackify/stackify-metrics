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

import com.stackify.metric.MetricManager;

/**
 * AbstractMetric
 * @author Eric Martin
 */
public abstract class AbstractMetric {

	/**
	 * Metric identity
	 */
	private final MetricIdentity identity;
		
	/**
	 * Constructor
	 * @param category Metric category
	 * @param name Metric name
	 * @param type Metric monitor type
	 */
	public AbstractMetric(final String category, final String name, final MetricMonitorType type) {
		this.identity = new MetricIdentity(category, name, type);
	}

	/**
	 * @return the identity
	 */
	public MetricIdentity getIdentity() {
		return identity;
	}

	/**
	 * Submits a metric to the collector
	 * @param value The value
	 * @param isIncrement True if the value in an increment
	 */
	protected void submit(final double value, final boolean isIncrement) {
		
		MetricCollector collector = MetricManager.getCollector();
		
		if (collector != null) {
			Metric.Builder builder = Metric.newBuilder();
			builder.identity(identity);
			builder.value(value);
			builder.isIncrement(isIncrement);
			
			Metric metric = builder.build();
			
			collector.submit(metric);
		}
	}
	
	/**
	 * Auto reports zero if the metric hasn't been modified
	 */
	protected void autoReportZero() {
		
		MetricCollector collector = MetricManager.getCollector();
		
		if (collector != null) {
			collector.autoReportZero(identity);
		}
	}
	
	/**
	 * Auto reports the last value if the metric hasn't been modified
	 */
	protected void autoReportLast() {
		
		MetricCollector collector = MetricManager.getCollector();
		
		if (collector != null) {
			collector.autoReportLast(identity);
		}
	}
}
