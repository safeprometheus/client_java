package com.github.safeprometheus.base;

import com.github.safeprometheus.HistogramWith1Label;
import com.github.safeprometheus.HistogramWith2Labels;
import com.github.safeprometheus.HistogramWith3Labels;
import com.github.safeprometheus.HistogramWith4Labels;
import com.github.safeprometheus.HistogramWith5Labels;
import com.github.safeprometheus.HistogramWithNoLabel;
import io.prometheus.client.Histogram;
import java.lang.String;

public class SafeHistogram {
  private Histogram.Builder builder = new Histogram.Builder();

  public HistogramWithNoLabel buildWithNoLabel(String metricName, String help) {
    this.builder.name(metricName);
    this.builder.help(help);
    return new HistogramWithNoLabel(builder.create().register());
  }

  public HistogramWith1Label buildWith1Label(String metricName, String help, String labelName1) {
    this.builder.name(metricName);
    this.builder.help(help);
    this.builder.labelNames(labelName1);
    return new HistogramWith1Label(builder.create().register());
  }

  public HistogramWith2Labels buildWith2Labels(String metricName, String help, String labelName1,
      String labelName2) {
    this.builder.name(metricName);
    this.builder.help(help);
    this.builder.labelNames(labelName1, labelName2);
    return new HistogramWith2Labels(builder.create().register());
  }

  public HistogramWith3Labels buildWith3Labels(String metricName, String help, String labelName1,
      String labelName2, String labelName3) {
    this.builder.name(metricName);
    this.builder.help(help);
    this.builder.labelNames(labelName1, labelName2, labelName3);
    return new HistogramWith3Labels(builder.create().register());
  }

  public HistogramWith4Labels buildWith4Labels(String metricName, String help, String labelName1,
      String labelName2, String labelName3, String labelName4) {
    this.builder.name(metricName);
    this.builder.help(help);
    this.builder.labelNames(labelName1, labelName2, labelName3, labelName4);
    return new HistogramWith4Labels(builder.create().register());
  }

  public HistogramWith5Labels buildWith5Labels(String metricName, String help, String labelName1,
      String labelName2, String labelName3, String labelName4, String labelName5) {
    this.builder.name(metricName);
    this.builder.help(help);
    this.builder.labelNames(labelName1, labelName2, labelName3, labelName4, labelName5);
    return new HistogramWith5Labels(builder.create().register());
  }

  public static SafeHistogram builder() {
    return new SafeHistogram();
  }

  public SafeHistogram buckets(double... buckets) {
    this.builder.buckets(buckets);
    return this;
  }
}
