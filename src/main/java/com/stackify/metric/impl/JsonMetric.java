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

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * JsonMetric
 * @author Eric Martin
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = JsonMetric.Builder.class)
public class JsonMetric {

	/**
	 * Stackify internal monitor id
	 */
	@JsonProperty("MonitorID")
	private final Integer monitorId;

	/**
	 * The metric value
	 */
	@JsonProperty("Value")
	private final Double value;

	/**
	 * The number of metrics that have been aggregated into this result
	 */
	@JsonProperty("Count")
	private final Integer count;

	/**
	 * Date/time of aggregate metric
	 */
	@JsonProperty("OccurredUtc")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'", timezone="UTC")
	private final Date occurredUtc;

	/**
	 * Type of stackify monitor
	 */
	@JsonProperty("MonitorTypeID")
	private final Integer monitorTypeId;

	/**
	 * @return the monitorId
	 */
	public Integer getMonitorId() {
		return monitorId;
	}

	/**
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * @return the occurredUtc
	 */
	public Date getOccurredUtc() {
		return occurredUtc;
	}

	/**
	 * @return the monitorTypeId
	 */
	public Integer getMonitorTypeId() {
		return monitorTypeId;
	}

	/**
	 * @param builder The Builder object that contains all of the values for initialization
	 */
	private JsonMetric(final Builder builder) {
	    this.monitorId = builder.monitorId;
	    this.value = builder.value;
	    this.count = builder.count;
	    this.occurredUtc = builder.occurredUtc;
	    this.monitorTypeId = builder.monitorTypeId;
	}

	/**
	 * @return A new instance of the Builder
	 */
	public static Builder newBuilder() {
	    return new Builder();
	}

	/**
	 * JsonMetric.Builder separates the construction of a JsonMetric from its representation
	 */
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Builder {

		/**
		 * The builder's monitorId
		 */
		@JsonProperty("MonitorID")
		private Integer monitorId;
		
		/**
		 * The builder's value
		 */
		@JsonProperty("Value")
		private Double value;
		
		/**
		 * The builder's count
		 */
		@JsonProperty("Count")
		private Integer count;
		
		/**
		 * The builder's occurredUtc
		 */
		@JsonProperty("OccurredUtc")
		@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'", timezone="UTC")
		private Date occurredUtc;
		
		/**
		 * The builder's monitorTypeId
		 */
		@JsonProperty("MonitorTypeID")
		private Integer monitorTypeId;
		
		/**
		 * Sets the builder's monitorId
		 * @param monitorId The monitorId to be set
		 * @return Reference to the current object
		 */
		public Builder monitorId(final Integer monitorId) {
		    this.monitorId = monitorId;
		    return this;
		}
		
		/**
		 * Sets the builder's value
		 * @param value The value to be set
		 * @return Reference to the current object
		 */
		public Builder value(final Double value) {
		    this.value = value;
		    return this;
		}
		
		/**
		 * Sets the builder's count
		 * @param count The count to be set
		 * @return Reference to the current object
		 */
		public Builder count(final Integer count) {
		    this.count = count;
		    return this;
		}
		
		/**
		 * Sets the builder's occurredUtc
		 * @param occurredUtc The occurredUtc to be set
		 * @return Reference to the current object
		 */
		public Builder occurredUtc(final Date occurredUtc) {
		    this.occurredUtc = occurredUtc;
		    return this;
		}
		
		/**
		 * Sets the builder's monitorTypeId
		 * @param monitorTypeId The monitorTypeId to be set
		 * @return Reference to the current object
		 */
		public Builder monitorTypeId(final Integer monitorTypeId) {
		    this.monitorTypeId = monitorTypeId;
		    return this;
		}
		
		/**
		 * @return A new object constructed from this builder
		 */
		public JsonMetric build() {
		    return new JsonMetric(this);
		}
	}
}
