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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stackify.api.common.concurrent.BackgroundService;
import com.stackify.api.common.util.Preconditions;

/**
 * MetricBackgroundService
 * @author Eric Martin
 */
public class MetricBackgroundService extends BackgroundService {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MetricBackgroundService.class);

	/**
	 * The scheduler that determines delay timing after errors
	 */
	private final MetricBackgroundServiceScheduler scheduler = new MetricBackgroundServiceScheduler();

	/**
	 * Metric collector
	 */
	private final MetricCollector collector;

	/**
	 * Sends metrics to Stackify
	 */
	private final MetricSender sender;

	/**
	 * Constructor
	 * @param collector Metric collector
	 * @param sender Sends metrics to Stackify
	 */
	public MetricBackgroundService(final MetricCollector collector, final MetricSender sender) {
		Preconditions.checkNotNull(collector);
		Preconditions.checkNotNull(sender);
		this.collector = collector;
		this.sender = sender;
	}
	
	/**
	 * @see com.stackify.api.common.concurrent.BackgroundService#startUp()
	 */
	@Override
	protected void startUp() {
	}

	/**
	 * @see com.google.common.util.concurrent.AbstractScheduledService#runOneIteration()
	 */
	@Override
	protected void runOneIteration() {
		try {
			int numSent = collector.flush(sender);
			scheduler.update(numSent);
		} catch (Throwable t) {
			LOGGER.info("Exception flushing metrics collector", t);
			scheduler.update(t);
		}
	}

	/**
	 * @see com.stackify.api.common.concurrent.BackgroundService#getNextScheduleDelayMilliseconds()
	 */
	@Override
	protected long getNextScheduleDelayMilliseconds() {
		return scheduler.getScheduleDelay();
	}

	/**
	 * @see com.google.common.util.concurrent.AbstractScheduledService#shutDown()
	 */
	@Override
	protected void shutDown() throws Exception {
		try {
			collector.flush(sender);
		} catch (Throwable t) {
			LOGGER.info("Exception flushing metrics collector during shut down", t);
		}
	}
}
