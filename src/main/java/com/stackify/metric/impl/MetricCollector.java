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

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.stackify.api.common.http.HttpException;
import com.stackify.api.common.lang.EvictingQueue;

/**
 * MetricCollector
 * @author Eric Martin
 */
public class MetricCollector {

	/**
	 * The service logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MetricCollector.class);

	/**
	 * Milliseconds in a minute
	 */
	private static final int MS_IN_MIN = 60000;

	/**
	 * The queue of objects to be transmitted
	 */
	private final Queue<Metric> queue = Queues.synchronizedQueue(new EvictingQueue<Metric>(1000)); 

	/**
	 * Initial values for the next iteration
	 */
	private final Map<MetricIdentity, Double> lastValues = Maps.newHashMap();
	
	/**
	 * Metrics that should auto report zero if there isn't a current value
	 */
	private final Set<MetricIdentity> autoReportZeroMetrics = Sets.newHashSet();
	
	/**
	 * Metrics that should auto report the last value if there isn't a current value
	 */
	private final Set<MetricIdentity> autoReportLastMetrics = Sets.newHashSet();
	
	/**
	 * The last time the collector was flushed
	 */
	private long lastFlush = 0;
	
	/**
	 * Adds this metric to the auto zero list
	 * @param identity The metric identity
	 */
	public void autoReportZero(final MetricIdentity identity) {
		Preconditions.checkNotNull(identity);
		autoReportZeroMetrics.add(identity);
	}
	
	/**
	 * Adds this metric to the auto last list
	 * @param identity The metric identity
	 */
	public void autoReportLast(final MetricIdentity identity) {
		Preconditions.checkNotNull(identity);
		autoReportLastMetrics.add(identity);
	}
	
	/**
	 * Submits a metric to the queue
	 * @param metric The metric
	 */
	public void submit(final Metric metric) {
		if (metric != null) {
			LOGGER.debug("Collecting metric: {}", metric);
			queue.offer(metric);
		}
	}
	
	/**
	 * Flushes all queued metrics to Stackify
	 * @param sender Responsible for sending to Stackify
	 * @return The number of metric aggregates sent to Stackify
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public int flush(final MetricSender sender) throws IOException, HttpException {
		Preconditions.checkNotNull(sender);
				
		// aggregate metrics that were enqueued before the current minute
		
		long currentMinute = (System.currentTimeMillis() / MS_IN_MIN) * MS_IN_MIN;
		
		LOGGER.debug("Flushing metrics < {}", currentMinute);
		
		MetricAggregator aggregator = new MetricAggregator(currentMinute, lastValues);
		
		while ((!queue.isEmpty()) && (queue.peek().getOccurredMillis() < currentMinute)) {
			aggregator.add(queue.remove());
		}

		// handle the auto reports
		
		if (lastFlush < currentMinute) {
			aggregator.autoReportZero(autoReportZeroMetrics);
			aggregator.autoReportLast(autoReportLastMetrics);
		}
		
		lastFlush = currentMinute;
		
		// get the aggregates
		
		List<MetricAggregate> aggregates = aggregator.getAggregates();

		// Save the values of gauge and average metrics for the next iteration
		// Gauge last value = aggregate last value
		// Average last value = aggregate last value / aggregate count
		
		if ((aggregates != null) && (!aggregates.isEmpty())) {
			for (MetricAggregate aggregate : aggregates) {
				if (aggregate.getIdentity().getType().equals(MetricMonitorType.GAUGE)) {
					lastValues.put(aggregate.getIdentity(), aggregate.getValue());
				} else if (aggregate.getIdentity().getType().equals(MetricMonitorType.AVERAGE)) {
					lastValues.put(aggregate.getIdentity(), aggregate.getValue() / aggregate.getCount());
				}
			}
		}
		
		// send the aggregates to Stackify
		
		int numSent = 0;
		
		if ((aggregates != null) && (!aggregates.isEmpty())) {
			LOGGER.debug("Sending aggregate metrics: {}", aggregates);
			sender.send(aggregates);
			numSent = aggregates.size();
		}
		
		return numSent;
	}
}
