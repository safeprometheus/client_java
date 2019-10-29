package com.github.safeprometheus;

import static com.github.safeprometheus.helper.StringHelper.nullSafe;

import io.prometheus.client.Gauge;
import java.lang.String;

public class GaugeWith5Labels {
  protected final Gauge gauge;

  public GaugeWith5Labels(String metricName, String metricHelp, String labelName1,
      String labelName2, String labelName3, String labelName4, String labelName5) {
    this.gauge = Gauge.build(metricName, metricHelp).labelNames(labelName1, labelName2, labelName3, labelName4, labelName5).create().register();
  }

  public void inc(String labelValue1, String labelValue2, String labelValue3, String labelValue4,
      String labelValue5) {
    this.gauge.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3), nullSafe(labelValue4), nullSafe(labelValue5)).inc();
  }

  public void inc(double amount, String labelValue1, String labelValue2, String labelValue3,
      String labelValue4, String labelValue5) {
    this.gauge.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3), nullSafe(labelValue4), nullSafe(labelValue5)).inc(amount);
  }

  public void dec(String labelValue1, String labelValue2, String labelValue3, String labelValue4,
      String labelValue5) {
    this.gauge.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3), nullSafe(labelValue4), nullSafe(labelValue5)).dec();
  }

  public void dec(double amount, String labelValue1, String labelValue2, String labelValue3,
      String labelValue4, String labelValue5) {
    this.gauge.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3), nullSafe(labelValue4), nullSafe(labelValue5)).dec(amount);
  }

  public void set(double val, String labelValue1, String labelValue2, String labelValue3,
      String labelValue4, String labelValue5) {
    this.gauge.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3), nullSafe(labelValue4), nullSafe(labelValue5)).set(val);
  }

  public void setToCurrentTime(String labelValue1, String labelValue2, String labelValue3,
      String labelValue4, String labelValue5) {
    this.gauge.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3), nullSafe(labelValue4), nullSafe(labelValue5)).setToCurrentTime();
  }

  public Gauge.Timer startTimer(String labelValue1, String labelValue2, String labelValue3,
      String labelValue4, String labelValue5) {
    return this.gauge.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3), nullSafe(labelValue4), nullSafe(labelValue5)).startTimer();
  }
}
