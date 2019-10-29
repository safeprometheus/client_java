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

## Installation

**Maven:** Put this in your `pom.xml`:
    
    <dependency>
      <groupId>com.github.safeprometheus</groupId>
      <artifactId>safeprometheus</artifactId>
      <version>1.0.0</version>
    </dependency>

