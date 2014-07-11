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

/**
 * CounterAndTimer
 * @author Eric Martin
 */
public class CounterAndTimer {

	/**
	 * The counter
	 */
	private final Counter counter;
	
	/**
	 * The timer
	 */
	private final Timer timer;
	
	/**
	 * Constructor
	 * @param category Metric category
	 * @param name Metric name
	 */
	public CounterAndTimer(String category, String name) {
		this.counter = new Counter(category, name);
		this.timer = new Timer(category, name + " Time");
	}
	
	/**
	 * @return the counter
	 */
	public Counter getCounter() {
		return counter;
	}

	/**
	 * @return the timer
	 */
	public Timer getTimer() {
		return timer;
	}

	/**
	 * Calculate time taken for operation
	 * @param start Start time of operation
	 */
	public void start(final Date start) {
		if (start != null) {
			counter.increment();
			timer.start(start);
		}
	}
	
	/**
	 * Calculate time taken for operation
	 * @param start Start time of operation (Unix epoch milliseconds)
	 */
	public void startMs(final long start) {
		counter.increment();
		timer.startMs(start);
	}
	
	/**
	 * Calculate time taken for operation
	 * @param duration Duration (milliseconds)
	 */
	public void durationMs(final long duration) {
		counter.increment();
		timer.durationMs(duration);
	}
	
	/**
	 * Auto reports zero if the metric hasn't been modified
	 * @return The current metric instance
	 */
	public CounterAndTimer autoReportZeroValue() {
		counter.autoReportZeroValue();
		timer.autoReportZeroValue();
		return this;
	}
}
