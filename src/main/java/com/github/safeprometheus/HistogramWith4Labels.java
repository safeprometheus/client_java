package com.github.safeprometheus;

import static com.github.safeprometheus.helper.StringHelper.nullSafe;

import io.prometheus.client.Histogram;
import java.lang.String;

public class HistogramWith4Labels {
  protected final Histogram histogram;

  public HistogramWith4Labels(String metricName, String metricHelp, String labelName1,
      String labelName2, String labelName3, String labelName4) {
    this.histogram = Histogram.build(metricName, metricHelp).labelNames(labelName1, labelName2, labelName3, labelName4).create().register();
  }

  public HistogramWith4Labels(Histogram histogramObject) {
    this.histogram = histogramObject;
  }

  public void observe(double val, String labelValue1, String labelValue2, String labelValue3,
      String labelValue4) {
    this.histogram.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3), nullSafe(labelValue4)).observe(val);
  }

  public Histogram.Timer startTimer(String labelValue1, String labelValue2, String labelValue3,
      String labelValue4) {
    return this.histogram.labels(nullSafe(labelValue1), nullSafe(labelValue2), nullSafe(labelValue3), nullSafe(labelValue4)).startTimer();
  }
}
