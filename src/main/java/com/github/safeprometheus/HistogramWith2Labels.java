package com.github.safeprometheus;

import static com.github.safeprometheus.helper.StringHelper.nullSafe;

import io.prometheus.client.Histogram;
import java.lang.String;

public class HistogramWith2Labels {
  protected final Histogram histogram;

  public HistogramWith2Labels(String metricName, String metricHelp, String labelName1,
      String labelName2) {
    this.histogram = Histogram.build(metricName, metricHelp).labelNames(labelName1, labelName2).create().register();
  }

  public HistogramWith2Labels(Histogram histogramObject) {
    this.histogram = histogramObject;
  }

  public void observe(double val, String labelValue1, String labelValue2) {
    this.histogram.labels(nullSafe(labelValue1), nullSafe(labelValue2)).observe(val);
  }

  public Histogram.Timer startTimer(String labelValue1, String labelValue2) {
    return this.histogram.labels(nullSafe(labelValue1), nullSafe(labelValue2)).startTimer();
  }
}
