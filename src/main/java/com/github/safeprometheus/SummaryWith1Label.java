package com.github.safeprometheus;

import static com.github.safeprometheus.helper.StringHelper.nullSafe;

import io.prometheus.client.Summary;
import java.lang.String;

public class SummaryWith1Label {
  protected final Summary summary;

  public SummaryWith1Label(String metricName, String metricHelp, String labelName1) {
    this.summary = Summary.build(metricName, metricHelp).labelNames(labelName1).create().register();
  }

  public SummaryWith1Label(Summary summaryObject) {
    this.summary = summaryObject;
  }

  public void observe(double val, String labelValue1) {
    this.summary.labels(nullSafe(labelValue1)).observe(val);
  }

  public Summary.Timer startTimer(String labelValue1) {
    return this.summary.labels(nullSafe(labelValue1)).startTimer();
  }
}
