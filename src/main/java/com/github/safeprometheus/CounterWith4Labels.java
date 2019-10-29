package com.github.safeprometheus;

import com.github.safeprometheus.helper.StringHelper;
import io.prometheus.client.Counter;
import java.lang.String;

public class CounterWith4Labels {
  protected final Counter counter;

  public CounterWith4Labels(String metricName, String metricHelp, String labelName1,
      String labelName2, String labelName3, String labelName4) {
    this.counter = Counter.build(metricName, metricHelp).labelNames(labelName1, labelName2, labelName3, labelName4).create().register();
  }

  public void inc(String labelValue1, String labelValue2, String labelValue3, String labelValue4) {
    this.counter.labels(StringHelper.nullSafe(labelValue1), StringHelper.nullSafe(labelValue2), StringHelper
        .nullSafe(labelValue3), StringHelper.nullSafe(labelValue4)).inc();
  }

  public void inc(double amount, String labelValue1, String labelValue2, String labelValue3,
      String labelValue4) {
    this.counter.labels(StringHelper.nullSafe(labelValue1), StringHelper.nullSafe(labelValue2), StringHelper
        .nullSafe(labelValue3), StringHelper.nullSafe(labelValue4)).inc(amount);
  }
}
