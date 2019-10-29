package com.github.safeprometheus;

import io.prometheus.client.Histogram;
import java.lang.String;

public class HistogramWithNoLabel {
  protected final Histogram histogram;

  public HistogramWithNoLabel(String metricName, String metricHelp) {
    this.histogram = Histogram.build(metricName, metricHelp).create().register();
  }

  public HistogramWithNoLabel(Histogram histogramObject) {
    this.histogram = histogramObject;
  }

  public void observe(double val) {
    this.histogram.observe(val);
  }

  public Histogram.Timer startTimer() {
    return this.histogram.startTimer();
  }
}
