package com.github.safeprometheus;

import static com.github.safeprometheus.helper.StringHelper.nullSafe;

import io.prometheus.client.Summary;
import java.lang.String;

public class SummaryWith3Labels {
  protected final Summary summary;

  public SummaryWith3Labels(String metricName, String metricHelp, String labelName1,
      String labelName2, String labelName3) {
    this.summary = Summary.build(metricName, metricHelp).labelNames(labelName1, labelName2, labelName3).create().register();
  }

  public SummaryWith3Labels(Summary summaryObject) {
    this.summary = summaryObject;
  }

  public void observe(double val, String labelValue1, String labelValue2, String labelValue3) {
    this.summary.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3)).observe(val);
  }

  public Summary.Timer startTimer(String labelValue1, String labelValue2, String labelValue3) {
    return this.summary.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3)).startTimer();
  }
}
