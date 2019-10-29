package com.github.safeprometheus.base;

import com.github.safeprometheus.CounterWith1Label;
import com.github.safeprometheus.CounterWith2Labels;
import com.github.safeprometheus.CounterWith3Labels;
import com.github.safeprometheus.CounterWith4Labels;
import com.github.safeprometheus.CounterWith5Labels;
import com.github.safeprometheus.CounterWithNoLabel;
import java.lang.String;

public class SafeCounter {
  public static CounterWithNoLabel createWithNoLabel(String metricName, String help) {
    return new CounterWithNoLabel(metricName, help);
  }

  public static CounterWith1Label createWith1Label(String metricName, String help,
      String labelName1) {
    return new CounterWith1Label(metricName, help, labelName1);
  }

  public static CounterWith2Labels createWith2Labels(String metricName, String help,
      String labelName1, String labelName2) {
    return new CounterWith2Labels(metricName, help, labelName1, labelName2);
  }

  public static CounterWith3Labels createWith3Labels(String metricName, String help,
      String labelName1, String labelName2, String labelName3) {
    return new CounterWith3Labels(metricName, help, labelName1, labelName2, labelName3);
  }

  public static CounterWith4Labels createWith4Labels(String metricName, String help,
      String labelName1, String labelName2, String labelName3, String labelName4) {
    return new CounterWith4Labels(metricName, help, labelName1, labelName2, labelName3, labelName4);
  }

  public static CounterWith5Labels createWith5Labels(String metricName, String help,
      String labelName1, String labelName2, String labelName3, String labelName4,
      String labelName5) {
    return new CounterWith5Labels(metricName, help, labelName1, labelName2, labelName3, labelName4, labelName5);
  }
}
