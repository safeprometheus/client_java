package com.github.safeprometheus.base;

import com.github.safeprometheus.SummaryWith1Label;
import com.github.safeprometheus.SummaryWith2Labels;
import com.github.safeprometheus.SummaryWith3Labels;
import com.github.safeprometheus.SummaryWith4Labels;
import com.github.safeprometheus.SummaryWith5Labels;
import com.github.safeprometheus.SummaryWithNoLabel;
import io.prometheus.client.Summary;
import java.lang.String;

public class SafeSummary {
  private Summary.Builder builder = new Summary.Builder();

  public SummaryWithNoLabel buildWithNoLabel(String metricName, String help) {
    this.builder.name(metricName);
    this.builder.help(help);
    return new SummaryWithNoLabel(builder.create().register());
  }

  public SummaryWith1Label buildWith1Label(String metricName, String help, String labelName1) {
    this.builder.name(metricName);
    this.builder.help(help);
    this.builder.labelNames(labelName1);
    return new SummaryWith1Label(builder.create().register());
  }

  public SummaryWith2Labels buildWith2Labels(String metricName, String help, String labelName1,
      String labelName2) {
    this.builder.name(metricName);
    this.builder.help(help);
    this.builder.labelNames(labelName1, labelName2);
    return new SummaryWith2Labels(builder.create().register());
  }

  public SummaryWith3Labels buildWith3Labels(String metricName, String help, String labelName1,
      String labelName2, String labelName3) {
    this.builder.name(metricName);
    this.builder.help(help);
    this.builder.labelNames(labelName1, labelName2, labelName3);
    return new SummaryWith3Labels(builder.create().register());
  }

  public SummaryWith4Labels buildWith4Labels(String metricName, String help, String labelName1,
      String labelName2, String labelName3, String labelName4) {
    this.builder.name(metricName);
    this.builder.help(help);
    this.builder.labelNames(labelName1, labelName2, labelName3, labelName4);
    return new SummaryWith4Labels(builder.create().register());
  }

  public SummaryWith5Labels buildWith5Labels(String metricName, String help, String labelName1,
      String labelName2, String labelName3, String labelName4, String labelName5) {
    this.builder.name(metricName);
    this.builder.help(help);
    this.builder.labelNames(labelName1, labelName2, labelName3, labelName4, labelName5);
    return new SummaryWith5Labels(builder.create().register());
  }

  public static SafeSummary builder() {
    return new SafeSummary();
  }

  public SafeSummary quantile(double quantile, double err) {
    this.builder.quantile(quantile, err);
    return this;
  }
}
