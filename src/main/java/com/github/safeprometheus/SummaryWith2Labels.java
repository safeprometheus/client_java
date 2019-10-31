package com.github.safeprometheus;

import static com.github.safeprometheus.helper.StringHelper.nullSafe;

import io.prometheus.client.Summary;
import java.lang.String;

public class SummaryWith2Labels {
  protected final Summary summary;

  public SummaryWith2Labels(String metricName, String metricHelp, String labelName1,
      String labelName2) {
    this.summary = Summary.build(metricName, metricHelp).labelNames(labelName1, labelName2).create().register();
  }

  public SummaryWith2Labels(Summary summaryObject) {
    this.summary = summaryObject;
  }

  public void observe(double val, String labelValue1, String labelValue2) {
    this.summary.labels(nullSafe(labelValue1), nullSafe(labelValue2)).observe(val);
  }

  public Summary.Timer startTimer(String labelValue1, String labelValue2) {
    return this.summary.labels(nullSafe(labelValue1), nullSafe(labelValue2)).startTimer();
  }
}
