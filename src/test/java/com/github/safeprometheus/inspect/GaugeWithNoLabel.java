package com.github.safeprometheus.inspect;

import io.prometheus.client.Gauge;


public class GaugeWithNoLabel extends com.github.safeprometheus.GaugeWithNoLabel {
  public GaugeWithNoLabel(String metricName, String metricHelp) {
    super(metricName, metricHelp);
  }

  public Gauge getGauge() {
    return this.gauge;
  }
}
