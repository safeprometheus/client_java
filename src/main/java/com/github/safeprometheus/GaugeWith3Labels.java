package com.github.safeprometheus;

import static com.github.safeprometheus.helper.StringHelper.nullSafe;

import io.prometheus.client.Gauge;
import java.lang.String;

public class GaugeWith3Labels {
  protected final Gauge gauge;

  public GaugeWith3Labels(String metricName, String metricHelp, String labelName1,
      String labelName2, String labelName3) {
    this.gauge = Gauge.build(metricName, metricHelp).labelNames(labelName1, labelName2, labelName3).create().register();
  }

  public void inc(String labelValue1, String labelValue2, String labelValue3) {
    this.gauge.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3)).inc();
  }

  public void inc(double amount, String labelValue1, String labelValue2, String labelValue3) {
    this.gauge.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3)).inc(amount);
  }

  public void dec(String labelValue1, String labelValue2, String labelValue3) {
    this.gauge.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3)).dec();
  }

  public void dec(double amount, String labelValue1, String labelValue2, String labelValue3) {
    this.gauge.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3)).dec(amount);
  }

  public void set(double val, String labelValue1, String labelValue2, String labelValue3) {
    this.gauge.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3)).set(val);
  }

  public void setToCurrentTime(String labelValue1, String labelValue2, String labelValue3) {
    this.gauge.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3)).setToCurrentTime();
  }

  public Gauge.Timer startTimer(String labelValue1, String labelValue2, String labelValue3) {
    return this.gauge.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3)).startTimer();
  }
}
