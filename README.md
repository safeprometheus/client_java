# Statically Typed, Safe Prometheus Client for Java

This library is created because original library has dangerous
behaviour when putting null value. For example, in the code
following, if `getMachineType` is `null`, it will throws
NullPointerException:

    private static final Gauge machineGauge = 
      Gauge.build("northwind_pending_job", "Total pending job")
           .labelNames("machine_type")
           .create()
           .build();

    machineGauge.labels(getMachineType()).inc()
    // Throws NullPointerException!

This may seems trivial. You may want to change the `getMachineType()`
signature to not return null. However, **you can add arbitrary number
of labels, even with wrong number of defined label.** This code
does not return compile error:

    private static final Gauge machineGauge = 
      Gauge.build("northwind_pending_job", "Total pending job")
           .labelNames("machine_type", "job_type")
           .create()
           .build();

    machineGauge.labels(nullSafe(getMachineType())).inc()
    // Throws NullPointerException! It requres job_type, the second
    // label. Meanwhile, we only define 1 label.

It is very easy to add or remove any label. However, that may lead
to NullPointerException. You may consider to use Checkstyle or similar
static-type tools. However, the original idea of this library is
we don't want to add more tools into our buildchain.

In SafePrometheus, this results in compile error:

    private static final GaugeWith1Label machineGauge = 
      SafeGauge.createWith2Label("northwind_pending_job", "Total pending job");
      
    machineGauge.inc(getMachineType())
    // the `inc` method is now requires two label parameters.
    
    machineGauge.inc(getMachineType(), getJobType());
    // Valid

In SafePrometheus, this does not yield exception:

    machineGauge.inc(getMachineType(), null);
    // Will results Prometheus metrics: northwind_pending_job{machine_type="production-cluster", job_type="null"}

This document is valid for Safeprometheus version 1.0.0.

## Installation

**Maven:** Put this in your `pom.xml`:
    
    <dependency>
      <groupId>com.github.safeprometheus</groupId>
      <artifactId>safeprometheus</artifactId>
      <version>1.0.1</version>
    </dependency>

For Gradle, SBT, and other build tools, please refer to [the Maven page](https://search.maven.org/artifact/com.github.safeprometheus/safeprometheus/1.0.1/jar).

## Version Base

<table>
  <thead>
    <th>prometheus/client_java version base</th>
    <th>safeprometheus/client_java version</th>
  </thead>
  <tbody>
    <tr>
      <td>0.8.0</td>
      <td>1.0.1</td>
    </tr>
  </tbody>
</table>

## Usage & Equivalent Syntax

If you never use the original prometheus/client_java, please refer
to [original documentation](https://github.com/prometheus/client_java).

For gauge and counter, simply put the label name/value when creating/
observing. Use `SafeGauge` and `SafeCounter` class. Refer to example
below for the equivalent syntax.

    Gauge gaugeOriginal = Gauge.build(name, help).labelNames(label1, label2).create().register();
    GaugeWith2Label original = SafeGauge.createWith2Label(name, help, label1, label2);
    
For Histogram, you can build directly with default buckets. Or, you
can put your own preferred bucket list.

    // Default buckets
    HistogramWithNoLabel histogram = SafeHistogram.builder().buildWithNoLabel(name, help);
    // Your own bucket
    HistogramWithNoLabel histogram =
      SafeHistogram.builder()
                   .buckets(0.1, 0.2, 0.5, 1.0, 2.0, 5.0, 10.0, 30.0)
                   .buildWithNoLabel(name, help);
                   
For Summary, you need to specify quantile and errors. Otherwise,
it will result in no metrics displayed. No exception will be thrown.

    Summary summaryOriginal = Summary.build("summaryTest", "the help")
        .labelNames("blah")
        .quantile(1.0d, 0.1d)
        .quantile(0.9d, 0.1d)
        .quantile(0.5d, 0.1d)
        .create();