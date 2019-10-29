package com.github.safeprometheus;

import static com.github.safeprometheus.helper.StringHelper.nullSafe;

import io.prometheus.client.Summary;
import java.lang.String;

public class SummaryWith4Labels {
  protected final Summary summary;

  public SummaryWith4Labels(String metricName, String metricHelp, String labelName1,
      String labelName2, String labelName3, String labelName4) {
    this.summary = Summary.build(metricName, metricHelp).labelNames(labelName1, labelName2, labelName3, labelName4).create().register();
  }

  public SummaryWith4Labels(Summary summaryObject) {
    this.summary = summaryObject;
  }

  public void observe(double val, String labelValue1, String labelValue2, String labelValue3,
      String labelValue4) {
    this.summary.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3), nullSafe(labelValue4)).observe(val);
  }

  public Summary.Timer startTimer(String labelValue1, String labelValue2, String labelValue3,
      String labelValue4) {
    return this.summary.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3), nullSafe(labelValue4)).startTimer();
  }
}
