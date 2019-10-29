package com.github.safeprometheus.generator;

import java.lang.reflect.Type;

public interface Generator {
  String getBaseName();

  String getTargetClassName(int totalLabel);

  Type getPrometheusClass();

  void buildClass(int totalLabels);

  void buildBaseClass(int maxLabelCount);
}
