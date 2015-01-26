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
 * Metric
 * @author Eric Martin
 */
public class Metric {

	/**
	 * Metric identity
	 */
	private final MetricIdentity identity;
	
	/**
	 * Occurred time (milliseconds)
	 */
	private final long occurredMillis = System.currentTimeMillis();
	
	/**
	 * The value
	 */
	private final double value;
	
	/**
	 * True if the value in an increment
	 */
	private final boolean isIncrement;

	/**
	 * @return the identity
	 */
	public MetricIdentity getIdentity() {
		return identity;
	}

	/**
	 * @return the occurredMillis
	 */
	public long getOccurredMillis() {
		return occurredMillis;
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @return the isIncrement
	 */
	public boolean isIncrement() {
		return isIncrement;
	}

	/**
	 * @param builder The Builder object that contains all of the values for initialization
	 */
	private Metric(final Builder builder) {
	    this.identity = builder.identity;
	    this.value = builder.value;
	    this.isIncrement = builder.isIncrement;
	}

	/**
	 * @return A new instance of the Builder
	 */
	public static Builder newBuilder() {
	    return new Builder();
	}

	/**
	 * Metric.Builder separates the construction of a Metric from its representation
	 */
	public static class Builder {

		/**
		 * The builder's identity
		 */
		private MetricIdentity identity;
				
		/**
		 * The builder's value
		 */
		private double value;
		
		/**
		 * The builder's isIncrement
		 */
		private boolean isIncrement;
		
		/**
		 * Sets the builder's identity
		 * @param identity The identity to be set
		 * @return Reference to the current object
		 */
		public Builder identity(final MetricIdentity identity) {
		    this.identity = identity;
		    return this;
		}
				
		/**
		 * Sets the builder's value
		 * @param value The value to be set
		 * @return Reference to the current object
		 */
		public Builder value(final double value) {
		    this.value = value;
		    return this;
		}
		
		/**
		 * Sets the builder's isIncrement
		 * @param isIncrement The isIncrement to be set
		 * @return Reference to the current object
		 */
		public Builder isIncrement(final boolean isIncrement) {
		    this.isIncrement = isIncrement;
		    return this;
		}
		
		/**
		 * @return A new object constructed from this builder
		 */
		public Metric build() {
		    return new Metric(this);
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Metric [identity=" + identity + ", occurredMillis="
				+ occurredMillis + ", value=" + value + ", isIncrement="
				+ isIncrement + "]";
	}
}
