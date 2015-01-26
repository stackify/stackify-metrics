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

import com.stackify.api.common.util.Preconditions;

/**
 * MetricAggregate
 * @author Eric Martin
 */
public class MetricAggregate {

	/**
	 * Metric identity
	 */
	private final MetricIdentity identity;
	
	/**
	 * Occurred time (milliseconds)
	 */
	private final long occurredMillis;
		
	/**
	 * The value
	 */
	private double value;
	
	/**
	 * Metric count
	 */
	private int count;
	
	/**
	 * Creates a MetricAggregate
	 * @param identity The metric identity
	 * @param currentMinute Current minute
	 * @return The MetricAggregate
	 */
	public static MetricAggregate fromMetricIdentity(final MetricIdentity identity, final long currentMinute) {
		Preconditions.checkNotNull(identity);
		return new MetricAggregate(identity, currentMinute);
	}
	
	/**
	 * Constructor
	 * @param identity The metric identity
	 * @param currentMinute Current minute
	 */
	private MetricAggregate(final MetricIdentity identity, final long currentMinute) {
		this.identity = identity;
		this.occurredMillis = currentMinute;
		this.value = 0.0;
		this.count = 0;
	}

	/**
	 * @return the identity
	 */
	public MetricIdentity getIdentity() {
		return identity;
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the occurredMillis
	 */
	public long getOccurredMillis() {
		return occurredMillis;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MetricAggregate [identity=" + identity + ", occurredMillis="
				+ occurredMillis + ", value=" + value + ", count=" + count
				+ "]";
	}
}
