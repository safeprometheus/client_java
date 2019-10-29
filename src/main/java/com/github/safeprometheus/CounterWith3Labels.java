package com.github.safeprometheus;

import com.github.safeprometheus.helper.StringHelper;
import io.prometheus.client.Counter;
import java.lang.String;

public class CounterWith3Labels {
  protected final Counter counter;

  public CounterWith3Labels(String metricName, String metricHelp, String labelName1,
      String labelName2, String labelName3) {
    this.counter = Counter.build(metricName, metricHelp).labelNames(labelName1, labelName2, labelName3).create().register();
  }

  public void inc(String labelValue1, String labelValue2, String labelValue3) {
    this.counter.labels(StringHelper.nullSafe(labelValue1), StringHelper.nullSafe(labelValue2), StringHelper
        .nullSafe(labelValue3)).inc();
  }

  public void inc(double amount, String labelValue1, String labelValue2, String labelValue3) {
    this.counter.labels(StringHelper.nullSafe(labelValue1), StringHelper.nullSafe(labelValue2), StringHelper
        .nullSafe(labelValue3)).inc(amount);
  }
}
