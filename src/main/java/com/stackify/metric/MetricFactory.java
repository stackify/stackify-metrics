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

/**
 * MetricFactory
 * @author Eric Martin
 */
public class MetricFactory {

	/**
	 * Returns a gauge metric
	 * @param category Metric category
	 * @param name Metric name
	 * @return Gauge metric
	 */
	public static Gauge getGauge(final String category, final String name) {
		return new Gauge(category, name);
	}
	
	/**
	 * Returns a counter metric
	 * @param category Metric category
	 * @param name Metric name
	 * @return Counter metric
	 */
	public static Counter getCounter(final String category, final String name) {
		return new Counter(category, name);
	}
	
	/**
	 * Returns a timer metric
	 * @param category Metric category
	 * @param name Metric name
	 * @return Timer metric
	 */
	public static Timer getTimer(final String category, final String name) {
		return new Timer(category, name);
	}
	
	/**
	 * Returns an average metric
	 * @param category Metric category
	 * @param name Metric name
	 * @return Average metric
	 */
	public static Average getAverage(final String category, final String name) {
		return new Average(category, name);
	}
	
	/**
	 * Returns a counter/timer metric
	 * @param category Metric category
	 * @param name Metric name
	 * @return CounterAndTimer metric
	 */
	public static CounterAndTimer getCounterAndTimer(final String category, final String name) {
		return new CounterAndTimer(category, name);
	}

	/**
	 * Hidden to prevent construction
	 */
	private MetricFactory() {
	}
}
