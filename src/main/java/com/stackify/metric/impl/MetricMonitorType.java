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

/**
 * MetricMonitorType
 * @author Eric Martin
 */
public enum MetricMonitorType {

	/**
	 * COUNTER
	 */
	COUNTER(129),
	
	/**
	 * TIMER
	 */
	TIMER(131),
	
	/**
	 * AVERAGE
	 */
	AVERAGE(132),
	
	/**
	 * GAUGE
	 */
	GAUGE(134);
	
	/**
	 * Id
	 */
	private final int id;
	
	/**
	 * Constructor
	 * @param id Id
	 */
	MetricMonitorType(final int id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
}
