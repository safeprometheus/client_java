package com.github.safeprometheus.generator;

import com.github.safeprometheus.helper.StringHelper;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeSpec;
import io.prometheus.client.Gauge;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.lang.model.element.Modifier;

public class GaugeGenerator implements Generator {

  private final static String GAUGE_NAME = "Gauge";

  @Override
  public String getBaseName() {
    return GAUGE_NAME;
  }

  @Override
  public String getTargetClassName(int totalLabels) {
    return getBaseName() + "With" + Util.inflectLabel(totalLabels);
  }

  @Override
  public Type getPrometheusClass() {
    return Gauge.class;
  }

  @Override
  public void buildClass(int totalLabels) {
    File targetFile = Util.getDefaultTarget();
    List<MethodSpec> methods = new ArrayList<MethodSpec>();

    Builder constructorMethod = MethodSpec.constructorBuilder()
        .addModifiers(Modifier.PUBLIC)
        .addParameter(String.class, "metricName")
        .addParameter(String.class, "metricHelp");

    for (int i = 0; i < totalLabels; i++) {
      constructorMethod.addParameter(ClassName.get(String.class), "labelName" + (i+1));
    }
    constructorMethod.addStatement(Util.metricBuildStatement(getBaseName(), totalLabels));
    methods.add(constructorMethod.build());

    methods.addAll(getProxyMethods(totalLabels));

    FieldSpec gaugeField = FieldSpec.builder(getPrometheusClass(), getBaseName().toLowerCase(), Modifier.PROTECTED, Modifier.FINAL).build();

    TypeSpec klazz = TypeSpec.classBuilder(getTargetClassName(totalLabels))
        .addModifiers(Modifier.PUBLIC)
        .addMethods(methods)
        .addField(gaugeField)
        .build();
    JavaFile result = JavaFile.builder("com.safeprometheus", klazz)
        .addStaticImport(StringHelper.class, "nullSafe")
        .build();

    try {
      result.writeTo(targetFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private List<MethodSpec> getProxyMethods(int totalLabels) {
    return Arrays.asList(
        Util.proxyMethod(this, "inc", totalLabels),
        Util.proxyMethod(this, "inc", double.class, "amount", totalLabels),
        Util.proxyMethod(this, "dec", totalLabels),
        Util.proxyMethod(this, "dec", double.class, "amount", totalLabels),
        Util.proxyMethod(this, "set", double.class, "val", totalLabels),
        Util.proxyMethod(this, "setToCurrentTime", totalLabels),
        Util.proxyMethod(this,  Gauge.Timer.class,"startTimer", totalLabels)
    );
  }

  public void buildBaseClass(int maxLabelCount) {
    String className = getBaseName();

    List<MethodSpec> methods = new ArrayList<MethodSpec>();
    File targetFile = Util.getDefaultTarget();

    for (int labelCount = 0; labelCount <= maxLabelCount; labelCount++) {

      ClassName klazzReturns = ClassName
          .get("com.safeprometheus", getTargetClassName(labelCount));

      Builder methodCreateWith = MethodSpec.methodBuilder("createWith" + Util.inflectLabel(labelCount) )
          .addModifiers(Modifier.PUBLIC)
          .addModifiers(Modifier.STATIC)
          .addParameter(String.class, "metricName")
          .addParameter(String.class, "help")
          .addStatement(Util.buildSafeConstructor(this, getTargetClassName(labelCount), labelCount))
          .returns(klazzReturns);

      for (int i = 0; i < labelCount; i++) {
        methodCreateWith.addParameter(ClassName.get(String.class), "labelName" + (i+1));
      }

      methods.add(methodCreateWith.build());
    }

    TypeSpec baseKlazz = TypeSpec.classBuilder("Safe" + className)
        .addMethods(methods)
        .addModifiers(Modifier.PUBLIC)
        .build();

    JavaFile base = JavaFile.builder("com.safeprometheus.base", baseKlazz).build();

    try {
      base.writeTo(targetFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
