package com.github.safeprometheus;

import static com.github.safeprometheus.helper.StringHelper.nullSafe;

import io.prometheus.client.Counter;
import java.lang.String;

public class CounterWith2Labels {
  protected final Counter counter;

  public CounterWith2Labels(String metricName, String metricHelp, String labelName1,
      String labelName2) {
    this.counter = Counter.build(metricName, metricHelp).labelNames(labelName1, labelName2).create().register();
  }

  public void inc(String labelValue1, String labelValue2) {
    this.counter.labels(nullSafe(labelValue1), nullSafe(labelValue2)).inc();
  }

  public void inc(double amount, String labelValue1, String labelValue2) {
    this.counter.labels(nullSafe(labelValue1), nullSafe(labelValue2)).inc(amount);
  }
}
