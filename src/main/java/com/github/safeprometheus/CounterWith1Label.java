package com.github.safeprometheus;

import static com.github.safeprometheus.helper.StringHelper.nullSafe;

import io.prometheus.client.Counter;
import java.lang.String;

public class CounterWith1Label {
  protected final Counter counter;

  public CounterWith1Label(String metricName, String metricHelp, String labelName1) {
    this.counter = Counter.build(metricName, metricHelp).labelNames(labelName1).create().register();
  }

  public void inc(String labelValue1) {
    this.counter.labels(nullSafe(labelValue1)).inc();
  }

  public void inc(double amount, String labelValue1) {
    this.counter.labels(nullSafe(labelValue1)).inc(amount);
  }
}
