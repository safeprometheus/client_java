package com.github.safeprometheus;

import static com.github.safeprometheus.helper.StringHelper.nullSafe;

import io.prometheus.client.Gauge;
import java.lang.String;

public class GaugeWith1Label {
  protected final Gauge gauge;

  public GaugeWith1Label(String metricName, String metricHelp, String labelName1) {
    this.gauge = Gauge.build(metricName, metricHelp).labelNames(labelName1).create().register();
  }

  public void inc(String labelValue1) {
    this.gauge.labels(nullSafe(labelValue1)).inc();
  }

  public void inc(double amount, String labelValue1) {
    this.gauge.labels(nullSafe(labelValue1)).inc(amount);
  }

  public void dec(String labelValue1) {
    this.gauge.labels(nullSafe(labelValue1)).dec();
  }

  public void dec(double amount, String labelValue1) {
    this.gauge.labels(nullSafe(labelValue1)).dec(amount);
  }

  public void set(double val, String labelValue1) {
    this.gauge.labels(nullSafe(labelValue1)).set(val);
  }

  public void setToCurrentTime(String labelValue1) {
    this.gauge.labels(nullSafe(labelValue1)).setToCurrentTime();
  }

  public Gauge.Timer startTimer(String labelValue1) {
    return this.gauge.labels(nullSafe(labelValue1)).startTimer();
  }
}
