package com.github.safeprometheus.inspect;

import io.prometheus.client.Gauge;

public class GaugeWith1Label extends com.github.safeprometheus.GaugeWith1Label {

  public GaugeWith1Label(String metricName, String metricHelp, String labelName1) {
    super(metricName, metricHelp, labelName1);
  }

  public Gauge getGauge() {
    return this.gauge;
  }
}
