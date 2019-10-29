package com.github.safeprometheus.base;

import com.github.safeprometheus.GaugeWith2Labels;
import com.github.safeprometheus.GaugeWith3Labels;
import com.github.safeprometheus.GaugeWith1Label;
import com.github.safeprometheus.GaugeWith4Labels;
import com.github.safeprometheus.GaugeWith5Labels;
import com.github.safeprometheus.GaugeWithNoLabel;
import java.lang.String;

public class SafeGauge {
  public static GaugeWithNoLabel createWithNoLabel(String metricName, String help) {
    return new GaugeWithNoLabel(metricName, help);
  }

  public static GaugeWith1Label createWith1Label(String metricName, String help,
      String labelName1) {
    return new GaugeWith1Label(metricName, help, labelName1);
  }

  public static GaugeWith2Labels createWith2Labels(String metricName, String help,
      String labelName1, String labelName2) {
    return new GaugeWith2Labels(metricName, help, labelName1, labelName2);
  }

  public static GaugeWith3Labels createWith3Labels(String metricName, String help,
      String labelName1, String labelName2, String labelName3) {
    return new GaugeWith3Labels(metricName, help, labelName1, labelName2, labelName3);
  }

  public static GaugeWith4Labels createWith4Labels(String metricName, String help,
      String labelName1, String labelName2, String labelName3, String labelName4) {
    return new GaugeWith4Labels(metricName, help, labelName1, labelName2, labelName3, labelName4);
  }

  public static GaugeWith5Labels createWith5Labels(String metricName, String help,
      String labelName1, String labelName2, String labelName3, String labelName4,
      String labelName5) {
    return new GaugeWith5Labels(metricName, help, labelName1, labelName2, labelName3, labelName4, labelName5);
  }
}
