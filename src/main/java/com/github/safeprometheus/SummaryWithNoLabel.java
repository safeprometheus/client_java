package com.github.safeprometheus;

import static com.github.safeprometheus.helper.StringHelper.nullSafe;

import io.prometheus.client.Summary;
import java.lang.String;

public class SummaryWithNoLabel {
  protected final Summary summary;

  public SummaryWithNoLabel(String metricName, String metricHelp) {
    this.summary = Summary.build(metricName, metricHelp).create().register();
  }

  public SummaryWithNoLabel(Summary summaryObject) {
    this.summary = summaryObject;
  }

  public void observe(double val) {
    this.summary.observe(val);
  }

  public Summary.Timer startTimer() {
    return this.summary.startTimer();
  }
}
