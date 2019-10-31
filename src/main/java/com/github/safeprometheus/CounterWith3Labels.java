package com.github.safeprometheus;

import static com.github.safeprometheus.helper.StringHelper.nullSafe;

import io.prometheus.client.Counter;
import java.lang.String;

public class CounterWith3Labels {
  protected final Counter counter;

  public CounterWith3Labels(String metricName, String metricHelp, String labelName1,
      String labelName2, String labelName3) {
    this.counter = Counter.build(metricName, metricHelp).labelNames(labelName1, labelName2, labelName3).create().register();
  }

  public void inc(String labelValue1, String labelValue2, String labelValue3) {
    this.counter.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3)).inc();
  }

  public void inc(double amount, String labelValue1, String labelValue2, String labelValue3) {
    this.counter.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3)).inc(amount);
  }
}
