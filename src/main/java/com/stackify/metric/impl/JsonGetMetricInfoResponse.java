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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * JsonGetMetricInfoResponse
 * @author Eric Martin
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = JsonGetMetricInfoResponse.Builder.class)
public class JsonGetMetricInfoResponse {

	/**
	 * The monitor id
	 */
	private final Integer monitorId;

	/**
	 * @return the monitorId
	 */
	public Integer getMonitorId() {
		return monitorId;
	}

	/**
	 * @param builder The Builder object that contains all of the values for initialization
	 */
	private JsonGetMetricInfoResponse(final Builder builder) {
	    this.monitorId = builder.monitorId;
	}

	/**
	 * @return A new instance of the Builder
	 */
	public static Builder newBuilder() {
	    return new Builder();
	}

	/**
	 * JsonGetMetricInfoResponse.Builder separates the construction of a JsonGetMetricInfoResponse from its representation
	 */
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Builder {

		/**
		 * The builder's monitorId
		 */
		@JsonProperty("MonitorID")
		private Integer monitorId;

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
		 * @return A new object constructed from this builder
		 */
		public JsonGetMetricInfoResponse build() {
		    return new JsonGetMetricInfoResponse(this);
		}
	}
}
