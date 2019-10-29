package com.github.safeprometheus;

import static com.github.safeprometheus.helper.StringHelper.nullSafe;

import io.prometheus.client.Histogram;
import java.lang.String;

public class HistogramWith3Labels {
  protected final Histogram histogram;

  public HistogramWith3Labels(String metricName, String metricHelp, String labelName1,
      String labelName2, String labelName3) {
    this.histogram = Histogram.build(metricName, metricHelp).labelNames(labelName1, labelName2, labelName3).create().register();
  }

  public HistogramWith3Labels(Histogram histogramObject) {
    this.histogram = histogramObject;
  }

  public void observe(double val, String labelValue1, String labelValue2, String labelValue3) {
    this.histogram.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3)).observe(val);
  }

  public Histogram.Timer startTimer(String labelValue1, String labelValue2, String labelValue3) {
    return this.histogram.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3)).startTimer();
  }
}
