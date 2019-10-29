package com.github.safeprometheus;

import com.github.safeprometheus.generator.GaugeGenerator;
import com.github.safeprometheus.generator.Generator;
import com.github.safeprometheus.generator.SummaryGenerator;
import com.github.safeprometheus.generator.CounterGenerator;
import com.github.safeprometheus.generator.HistogramGenerator;
import java.util.Arrays;
import java.util.List;

public class BuildTemplate {
  private static final int MAX_LABEL_COUNT = 5;

  public static void main(String... args) {
    List<Generator> targets = Arrays
        .asList(new CounterGenerator(), new GaugeGenerator(), new HistogramGenerator(),
            new SummaryGenerator());

    targets.stream().forEach(BuildTemplate::generateClass);
  }

  private static void generateClass(Generator generator) {
    // Create the base
    generator.buildBaseClass(MAX_LABEL_COUNT);;

    for (int labelCount = 0; labelCount <= MAX_LABEL_COUNT; labelCount++) {
      generator.buildClass(labelCount);
    }
  }
}
