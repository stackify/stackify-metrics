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

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * MetricIdentity
 * @author Eric Martin
 */
public class MetricIdentity {

	/**
	 * Metric category
	 */
	private final String category;
	
	/**
	 * Metric name
	 */
	private final String name;
	
	/**
	 * Metric monitor type
	 */
	private final MetricMonitorType type;
	
	/**
	 * Constructor
	 * @param category Metric category
	 * @param name Metric name
	 * @param type Metric monitor type
	 */
	public MetricIdentity(final String category, final String name, final MetricMonitorType type) {
		Preconditions.checkNotNull(category);
		Preconditions.checkArgument(!category.isEmpty());
		Preconditions.checkNotNull(name);
		Preconditions.checkArgument(!name.isEmpty());
		Preconditions.checkNotNull(type);
		
		this.category = category;
		this.name = name;
		this.type = type;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the type
	 */
	public MetricMonitorType getType() {
		return type;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 * @return A hash code of this object
	 */
	@Override
	public int hashCode() {
	    return Objects.hashCode(category, name, type);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @param other The reference object with which to compare
	 * @return True if this object is the same as the other object, false otherwise
	 */
	@Override
	public boolean equals(final Object other) {
	    if (other == this) {
	        return true;
	    }
	    
	    if (!(other instanceof MetricIdentity)) {
	        return false;
	    }
	    
	    final MetricIdentity otherMetricIdentity = (MetricIdentity) other;
	    
	    return Objects.equal(category, otherMetricIdentity.category)
	        && Objects.equal(name, otherMetricIdentity.name)
	        && Objects.equal(type, otherMetricIdentity.type);
	}

	/**
	 * @see java.lang.Object#toString()
	 * @return A string representation of the object
	 */
	@Override
	public String toString() {
	    return Objects.toStringHelper(this)
	                  .omitNullValues()
	                  .add("category", category)
	                  .add("name", name)
	                  .add("type", type)
	                  .toString();
	}
}
