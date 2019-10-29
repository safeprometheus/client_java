package com.github.safeprometheus.inspect;

import io.prometheus.client.Summary;

public class SummaryWith1Label extends com.github.safeprometheus.SummaryWith1Label {

  public SummaryWith1Label(Summary summaryObject) {
    super(summaryObject);
  }

  public Summary getSummary() {
    return this.summary;
  }
}
