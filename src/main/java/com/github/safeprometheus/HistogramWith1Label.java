package com.github.safeprometheus;

import static com.github.safeprometheus.helper.StringHelper.nullSafe;

import io.prometheus.client.Histogram;
import java.lang.String;

public class HistogramWith1Label {
  protected final Histogram histogram;

  public HistogramWith1Label(String metricName, String metricHelp, String labelName1) {
    this.histogram = Histogram.build(metricName, metricHelp).labelNames(labelName1).create().register();
  }

  public HistogramWith1Label(Histogram histogramObject) {
    this.histogram = histogramObject;
  }

  public void observe(double val, String labelValue1) {
    this.histogram.labels(nullSafe(labelValue1)).observe(val);
  }

  public Histogram.Timer startTimer(String labelValue1) {
    return this.histogram.labels(nullSafe(labelValue1)).startTimer();
  }
}
