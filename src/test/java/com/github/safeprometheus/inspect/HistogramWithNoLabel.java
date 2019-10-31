package com.github.safeprometheus.inspect;

import io.prometheus.client.Histogram;

public class HistogramWithNoLabel extends com.github.safeprometheus.HistogramWithNoLabel {
  public HistogramWithNoLabel(Histogram histogramObject) {
    super(histogramObject);
  }
  public Histogram getHistogram() {
    return this.histogram;
  }
}
