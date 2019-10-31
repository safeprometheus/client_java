package com.github.safeprometheus;

import static com.github.safeprometheus.helper.StringHelper.nullSafe;

import io.prometheus.client.Counter;
import java.lang.String;

public class CounterWithNoLabel {
  protected final Counter counter;

  public CounterWithNoLabel(String metricName, String metricHelp) {
    this.counter = Counter.build(metricName, metricHelp).create().register();
  }

  public void inc() {
    this.counter.inc();
  }

  public void inc(double amount) {
    this.counter.inc(amount);
  }
}
