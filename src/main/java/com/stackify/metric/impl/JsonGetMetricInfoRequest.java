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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JsonGetMetricInfoRequest
 * @author Eric Martin
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonGetMetricInfoRequest {

	@JsonProperty("Category")
    private final String category;
    
    @JsonProperty("MetricName")
    private final String metricName;
    
    @JsonProperty("DeviceID")
    private final Integer deviceId;
    
    @JsonProperty("DeviceAppID")
    private final Integer deviceAppId;
    
    @JsonProperty("AppNameID")
    private final String appNameId;

    @JsonProperty("MetricTypeID")
    private final Integer metricTypeId;

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @return the metricName
	 */
	public String getMetricName() {
		return metricName;
	}

	/**
	 * @return the deviceId
	 */
	public Integer getDeviceId() {
		return deviceId;
	}

	/**
	 * @return the deviceAppId
	 */
	public Integer getDeviceAppId() {
		return deviceAppId;
	}

	/**
	 * @return the appNameId
	 */
	public String getAppNameId() {
		return appNameId;
	}

	/**
	 * @return the metricTypeId
	 */
	public Integer getMetricTypeId() {
		return metricTypeId;
	}

	/**
	 * @param builder The Builder object that contains all of the values for initialization
	 */
	private JsonGetMetricInfoRequest(final Builder builder) {
	    this.category = builder.category;
	    this.metricName = builder.metricName;
	    this.deviceId = builder.deviceId;
	    this.deviceAppId = builder.deviceAppId;
	    this.appNameId = builder.appNameId;
	    this.metricTypeId = builder.metricTypeId;
	}

	/**
	 * @return A new instance of the Builder
	 */
	public static Builder newBuilder() {
	    return new Builder();
	}

	/**
	 * JsonGetMetricInfoRequest.Builder separates the construction of a JsonGetMetricInfoRequest from its representation
	 */
	public static class Builder {

		/**
		 * The builder's category
		 */
		private String category;
		
		/**
		 * The builder's metricName
		 */
		private String metricName;
		
		/**
		 * The builder's deviceId
		 */
		private Integer deviceId;
		
		/**
		 * The builder's deviceAppId
		 */
		private Integer deviceAppId;
		
		/**
		 * The builder's appNameId
		 */
		private String appNameId;
		
		/**
		 * The builder's metricTypeId
		 */
		private Integer metricTypeId;
		
		/**
		 * Sets the builder's category
		 * @param category The category to be set
		 * @return Reference to the current object
		 */
		public Builder category(final String category) {
		    this.category = category;
		    return this;
		}
		
		/**
		 * Sets the builder's metricName
		 * @param metricName The metricName to be set
		 * @return Reference to the current object
		 */
		public Builder metricName(final String metricName) {
		    this.metricName = metricName;
		    return this;
		}
		
		/**
		 * Sets the builder's deviceId
		 * @param deviceId The deviceId to be set
		 * @return Reference to the current object
		 */
		public Builder deviceId(final Integer deviceId) {
		    this.deviceId = deviceId;
		    return this;
		}
		
		/**
		 * Sets the builder's deviceAppId
		 * @param deviceAppId The deviceAppId to be set
		 * @return Reference to the current object
		 */
		public Builder deviceAppId(final Integer deviceAppId) {
		    this.deviceAppId = deviceAppId;
		    return this;
		}
		
		/**
		 * Sets the builder's appNameId
		 * @param appNameId The appNameId to be set
		 * @return Reference to the current object
		 */
		public Builder appNameId(final String appNameId) {
		    this.appNameId = appNameId;
		    return this;
		}
		
		/**
		 * Sets the builder's metricTypeId
		 * @param metricTypeId The metricTypeId to be set
		 * @return Reference to the current object
		 */
		public Builder metricTypeId(final Integer metricTypeId) {
		    this.metricTypeId = metricTypeId;
		    return this;
		}
		
		/**
		 * @return A new object constructed from this builder
		 */
		public JsonGetMetricInfoRequest build() {
		    return new JsonGetMetricInfoRequest(this);
		}
	}
}
