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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stackify.api.common.util.Preconditions;

/**
 * MetricAggregator
 * @author Eric Martin
 */
public class MetricAggregator {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MetricAggregator.class);

	/**
	 * Map from metric key to utc minute to metric aggregate)
	 */
	private final Map<MetricIdentity, Map<Long, MetricAggregate>> aggregates = new HashMap<MetricIdentity, Map<Long, MetricAggregate>>();
	
	/**
	 * Current minute
	 */
	private final long currentMinute;
	
	/**
	 * Last values for the metrics
	 */
	private final Map<MetricIdentity, Double> lastValues;

	/**
	 * Constructor
	 * @param currentMinute Current minute
	 * @param lastValues Last values for the metrics
	 */
	public MetricAggregator(final long currentMinute, final Map<MetricIdentity, Double> lastValues) {
		Preconditions.checkArgument(0 < currentMinute);
		Preconditions.checkNotNull(lastValues);
		this.currentMinute = currentMinute;
		this.lastValues = lastValues;
	}
	
	/**
	 * @return All aggregate metrics
	 */
	public List<MetricAggregate> getAggregates() {
		
		List<MetricAggregate> flatAggregates = new ArrayList<MetricAggregate>();

		for (Map<Long, MetricAggregate> metricAggregates : aggregates.values()) {		    
			for (MetricAggregate aggregate : metricAggregates.values()) {
				flatAggregates.add(aggregate);
			}
		}
		
		return flatAggregates;
	}
	
	/**
	 * Aggregates a single metric into the collection of aggregates
	 * @param metric The metric
	 */
	public void add(final Metric metric) {
		Preconditions.checkNotNull(metric);
		
		// get the aggregate for this minute
		
		MetricAggregate aggregate = getAggregate(metric.getIdentity());
		
		// add the current metric into the aggregate
		
		switch (metric.getIdentity().getType()) {
			case COUNTER: 
			case TIMER: 
			case AVERAGE:
				aggregate.setCount(aggregate.getCount() + 1);
				aggregate.setValue(aggregate.getValue() + metric.getValue());
			    break;
			case GAUGE:
				aggregate.setCount(1);
				if (metric.isIncrement()) {
					aggregate.setValue(aggregate.getValue() + metric.getValue());
				} else {
					aggregate.setValue(metric.getValue());
				}
			    break;	
			default:
				LOGGER.info("Unable to aggregate {} metric type", metric.getIdentity().getType());
				break;
		}
	}	
	
	/**
	 * Auto reports any metrics that are missing a value from the current iteration
	 * @param autoMetrics The metrics that need to me auto reported as zero
	 */
	public void autoReportZero(final Set<MetricIdentity> autoMetrics) {
		Preconditions.checkNotNull(autoMetrics);
		
		for (MetricIdentity identity : autoMetrics) {
			if (!aggregateExistsForCurrentMinute(identity)) {
				MetricAggregate aggregate = getAggregate(identity);
				
				aggregate.setCount(1);
				aggregate.setValue(0.0);
			}
		}
	}
	
	/**
	 * Auto reports any metrics that are missing a value from the current iteration
	 * @param autoMetrics The metrics that need to me auto reported as the last value
	 */
	public void autoReportLast(final Set<MetricIdentity> autoMetrics) {
		Preconditions.checkNotNull(autoMetrics);
		
		for (MetricIdentity identity : autoMetrics) {
			if (!aggregateExistsForCurrentMinute(identity)) {
				MetricAggregate aggregate = getAggregate(identity);
				
				aggregate.setCount(1);
				
				if (lastValues.containsKey(identity)) {
					aggregate.setValue(lastValues.get(identity));
				} else {
					aggregate.setValue(0.0);					
				}
			}
		}
	}

	/**
	 * Determines if the specified aggregate metric exists for the current minute
	 * @param identity The metric identity
	 * @return True if the metric exists, false otherwise
	 */
	private boolean aggregateExistsForCurrentMinute(final MetricIdentity identity) {
		Preconditions.checkNotNull(identity);
		
		if (aggregates.containsKey(identity)) {			
			if (aggregates.get(identity).containsKey(currentMinute)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Retrieves the specified metric for the current minute
	 * @param metric The metric identity
	 * @return The metric aggregate for the metric at the current minute
	 */
	private MetricAggregate getAggregate(final MetricIdentity identity) {

		// get the map from utc minute to aggregate for this metric

		if (!aggregates.containsKey(identity)) {
			aggregates.put(identity, new HashMap<Long, MetricAggregate>());			
		}
		
		Map<Long, MetricAggregate> metricAggregates = aggregates.get(identity);
		
		// get the aggregate for this minute
		
		if (!metricAggregates.containsKey(currentMinute)) {
		
			MetricAggregate initialAggregate = MetricAggregate.fromMetricIdentity(identity, currentMinute);
			
			if (identity.getType().equals(MetricMonitorType.GAUGE)) {
				if (lastValues.containsKey(identity)) {
					initialAggregate.setValue(lastValues.get(identity));
				}
			}
						
			metricAggregates.put(currentMinute, initialAggregate);
		}

		MetricAggregate aggregate = metricAggregates.get(currentMinute);
		
		return aggregate;
	}
}
