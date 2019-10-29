package com.github.safeprometheus;

import io.prometheus.client.Summary;
import org.junit.Assert;
import org.junit.Test;

public class SummaryTest {
  @Test
  public void shouldHaveCorrectValue() {
    Summary summaryOriginal = Summary.build("summaryTest", "the help")
        .labelNames("blah")
        .quantile(1.0d, 0.1d)
        .quantile(0.9d, 0.1d)
        .quantile(0.5d, 0.1d)
        .create();
    SummaryWith1Label summary = new SummaryWith1Label(summaryOriginal);

    summary.observe(10.d, "taskType1");
    summary.observe(50.d, "taskType1");
    summary.observe(40.d, "taskType1");
    summary.observe(40.d, "taskType1");
    summary.observe(30.d, "taskType2");

    Assert.assertEquals(40.d, summary.summary.labels("taskType1").get().quantiles.values().iterator().next(), 1e-10);
    Assert.assertEquals(30.d, summary.summary.labels("taskType2").get().quantiles.values().iterator().next(), 1e-10);
  }
}
