package com.github.safeprometheus;

import org.junit.Assert;
import org.junit.Test;

public class GaugeTest {

  @Test
  public void shouldHaveCorrectValue() {
    GaugeWith1Label gauge = new GaugeWith1Label("gaugeWithLabel", "help", "machineName");
    gauge.inc(5.d, "kudos");
    gauge.inc(2.d, "kudos");
    gauge.dec(2.d, "muso");

    Assert.assertEquals(7.d, gauge.gauge.labels("kudos").get(), 1e-10);
    Assert.assertEquals(-2.d, gauge.gauge.labels("muso").get(), 1e-10);
  }

  @Test
  public void canBuiltWithNoLabel() {
    GaugeWithNoLabel gauge = new GaugeWithNoLabel("gaugeNoLabel", "help");
    gauge.inc(5.d);
    gauge.inc(2.d);
    gauge.dec(10.d);

    Assert.assertEquals(-3.d, gauge.gauge.get(), 1e-10);
  }

  @Test
  public void allowNullLabel() {
    GaugeWith1Label gauge = new GaugeWith1Label("gaugeTest3", "help", "machineName");
    gauge.inc(5.d, "kudos");
    gauge.inc(2.d, "kudos");
    gauge.dec(2.d, null);

    Assert.assertEquals(7.d, gauge.gauge.labels("kudos").get(), 1e-10);
    Assert.assertEquals(-2.d, gauge.gauge.labels("null").get(), 1e-10);
  }

  @Test(expected=NullPointerException.class)
  public void originalMethodThrowsException() {
    GaugeWith1Label gauge = new GaugeWith1Label("gaugeTest4s", "help", "machineName");
    gauge.gauge.inc(5.d);
  }

  @Test(expected=NullPointerException.class)
  public void originalMethodThrowsException2() {
    GaugeWith1Label gauge = new GaugeWith1Label("gaugeTest5", "help", "machineName");
    gauge.gauge.labels(null).inc(5.d);
  }

}
