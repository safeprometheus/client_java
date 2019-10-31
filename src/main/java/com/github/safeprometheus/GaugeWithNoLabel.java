package com.github.safeprometheus;

import static com.github.safeprometheus.helper.StringHelper.nullSafe;

import io.prometheus.client.Gauge;
import java.lang.String;

public class GaugeWithNoLabel {
  protected final Gauge gauge;

  public GaugeWithNoLabel(String metricName, String metricHelp) {
    this.gauge = Gauge.build(metricName, metricHelp).create().register();
  }

  public void inc() {
    this.gauge.inc();
  }

  public void inc(double amount) {
    this.gauge.inc(amount);
  }

  public void dec() {
    this.gauge.dec();
  }

  public void dec(double amount) {
    this.gauge.dec(amount);
  }

  public void set(double val) {
    this.gauge.set(val);
  }

  public void setToCurrentTime() {
    this.gauge.setToCurrentTime();
  }

  public Gauge.Timer startTimer() {
    return this.gauge.startTimer();
  }
}
