package com.github.safeprometheus;

import com.github.safeprometheus.base.SafeHistogram;
import org.junit.Assert;
import org.junit.Test;

public class HistogramTest {
  @Test
  public void shouldHaveCorrectValue() {
    HistogramWithNoLabel histogram = SafeHistogram.builder()
        .buckets(0.1, 0.2, 0.5, 1, 2, 3, 5)
        .buildWithNoLabel("histogram_tesst", "help");

    histogram.observe(1.d);
    histogram.observe(0.7d);
    histogram.observe(0.7d);
    histogram.observe(0.9d);
    histogram.observe(2.5d);

    Assert.assertEquals(5.8, histogram.histogram.labels().get().sum, 1e-10);
  }
}
