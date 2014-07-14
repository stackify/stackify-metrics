# stackify-metrics

[![Build Status](https://travis-ci.org/stackify/stackify-metrics.png)](https://travis-ci.org/stackify/stackify-metrics)
[![Coverage Status](https://coveralls.io/repos/stackify/stackify-metrics/badge.png)](https://coveralls.io/r/stackify/stackify-metrics)

API for sending custom metrics to Stackify.

Custom Metrics Overview:

https://stackify.screenstepslive.com/s/3095/m/7787/l/207264-custom-application-metrics-api

Sign Up for a Trial:

http://www.stackify.com/sign-up/

## Usage

There are four different types of metrics: 
* Gauge: Keeps track of the last value that was set in the current minute
* Counter: Calculates the rate per minute
* Average: Calculates the average of all values in the current minute
* Timer: Calculates the average elapsed time for an operation in the current minute
* CounterAndTimer: Composite of the Counter and Timer metrics for convenience

All metrics are identified with a category and name. We will group metrics with the same category when they are displayed in Stackify. Use the MetricFactory to get different types of metrics. See below for more details on the operations available for each type of metric. 
```java
Gauge gauge = MetricFactory.getGauge("MyCategory", "MyGauge");
...
```

Be sure to properly shutdown to flush any queued metrics and shutdown the background thread:
```java
MetricManager.shutdown();
```

#### Configuration

You need a stackify-api.properties file on your classpath that defines the configuration required for the Metrics API:
```
stackify.apiKey=YOUR_API_KEY
stackify.application=YOUR_APPLICATION_NAME
stackify.environment=YOUR_ENVIRONMENT
```

#### Gauge Metric

```java
// Get a Gauge metric
Gauge gauge = MetricFactory.getGauge("MyCategory", "MyGauge");
...

// Set the value of the metric to v
gauge.set(v);
...

// Adds v to the current value of the metric
gauge.add(v);
...

// Subtracts v from the current value of the metric
gauge.subtract(v);
...

// Automatically report 0 for the value of the metric if not set
gauge.autoReportZeroValue();
...

// Automatically report the last value for this metric if not set
gauge.autoReportLastValue();
...
```

#### Counter Metric

```java
// Get a Counter metric
Counter counter = MetricFactory.getCounter("MyCategory", "MyCounter");
...

// Adds 1 to the current value of the metric
counter.increment();
...

// Adds v to the current value of the metric
counter.increment(v);
...

// Subtracts 1 from the current value of the metric
counter.decrement();
...

// Subtracts v from the current value of the metric
counter.decrement(v);
...

// Adds v to the current value of the metric
counter.add(v);
...

// Subtracts v from the current value of the metric
counter.subtract(v);
...

// Automatically report 0 for the value of the metric if not set
counter.autoReportZeroValue();
...
```

#### Average Metric

```java
// Get a Average metric
Average average = MetricFactory.getAverage("MyCategory", "MyAverage");
...

// Adds a value to the average metric
average.addValue(v);
...

// Automatically report 0 for the value of the metric if not set
average.autoReportZeroValue();
...

// Automatically report the last value for this metric if not set
average.autoReportLastValue();
...
```

#### Timer Metric

```java
// Get a Timer metric
Timer timer = MetricFactory.getTimer("MyCategory", "MyTimer");
...

// Calculates the time taken for this operation using the start time d (java.util.Date)
timer.start(d);
...

// Calculates the time taken for this operation using the start time d (milliseconds)
timer.startMs(d);
...

// Sets the time taken for this operation to d (milliseconds)
timer.durationMs(d);
...

// Automatically report 0 for the value of the metric if not set
timer.autoReportZeroValue();
...
```

#### CounterAndTimer Metric

```java
// Get a CounterAndTimer metric (composite of a Counter metric and Timer metric)
CounterAndTimer counterAndTimer = MetricFactory.getCounterAndTimer("MyCategory", "MyCounterAndTimer");
...

// Counter: Adds 1 to the current value of the metric
// Timer: Calculates the time taken for this operation using the start time d (java.util.Date)
counterAndTimer.start(d);
...

// Counter: Adds 1 to the current value of the metric
// Timer: Calculates the time taken for this operation using the start time d (milliseconds)
counterAndTimer.startMs(d);
...

// Counter: Adds 1 to the current value of the metric
// Timer: Sets the time taken for this operation to d (milliseconds)
counterAndTimer.durationMs(d);
...

// Automatically report 0 for the value of the metric if not set
counterAndTimer.autoReportZeroValue();
...
```

## Installation

Add it as a maven dependency:
```xml
<dependency>
    <groupId>com.stackify</groupId>
    <artifactId>stackify-metrics</artifactId>
    <version>1.0.0</version>
</dependency>
```

## License

Copyright 2014 Stackify, LLC.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.