package com.github.safeprometheus;

import static com.github.safeprometheus.helper.StringHelper.nullSafe;

import io.prometheus.client.Gauge;
import java.lang.String;

public class GaugeWith4Labels {
  protected final Gauge gauge;

  public GaugeWith4Labels(String metricName, String metricHelp, String labelName1,
      String labelName2, String labelName3, String labelName4) {
    this.gauge = Gauge.build(metricName, metricHelp).labelNames(labelName1, labelName2, labelName3, labelName4).create().register();
  }

  public void inc(String labelValue1, String labelValue2, String labelValue3, String labelValue4) {
    this.gauge.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3), nullSafe(labelValue4)).inc();
  }

  public void inc(double amount, String labelValue1, String labelValue2, String labelValue3,
      String labelValue4) {
    this.gauge.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3), nullSafe(labelValue4)).inc(amount);
  }

  public void dec(String labelValue1, String labelValue2, String labelValue3, String labelValue4) {
    this.gauge.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3), nullSafe(labelValue4)).dec();
  }

  public void dec(double amount, String labelValue1, String labelValue2, String labelValue3,
      String labelValue4) {
    this.gauge.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3), nullSafe(labelValue4)).dec(amount);
  }

  public void set(double val, String labelValue1, String labelValue2, String labelValue3,
      String labelValue4) {
    this.gauge.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3), nullSafe(labelValue4)).set(val);
  }

  public void setToCurrentTime(String labelValue1, String labelValue2, String labelValue3,
      String labelValue4) {
    this.gauge.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3), nullSafe(labelValue4)).setToCurrentTime();
  }

  public Gauge.Timer startTimer(String labelValue1, String labelValue2, String labelValue3,
      String labelValue4) {
    return this.gauge.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3), nullSafe(labelValue4)).startTimer();
  }
}
