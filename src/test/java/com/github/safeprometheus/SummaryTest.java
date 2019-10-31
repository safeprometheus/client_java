package com.github.safeprometheus;

import com.github.safeprometheus.base.SafeSummary;
import org.junit.Assert;
import org.junit.Test;

public class SummaryTest {
  @Test
  public void shouldHaveCorrectValue() {
    SummaryWith1Label summary = SafeSummary.builder()
        .quantile(1.0d, 0.1d)
        .quantile(0.9d, 0.1d)
        .quantile(0.5d, 0.1d)
        .buildWith1Label("summary_test", "help", "firstLabel");

    summary.observe(10.d, "taskType1");
    summary.observe(50.d, "taskType1");
    summary.observe(40.d, "taskType1");
    summary.observe(40.d, "taskType1");
    summary.observe(30.d, "taskType2");

    Assert.assertEquals(40.d, summary.summary.labels("taskType1").get().quantiles.values().iterator().next(), 1e-10);
    Assert.assertEquals(30.d, summary.summary.labels("taskType2").get().quantiles.values().iterator().next(), 1e-10);
  }
}
