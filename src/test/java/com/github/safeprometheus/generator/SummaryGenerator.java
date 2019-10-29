package com.github.safeprometheus.generator;

import com.github.safeprometheus.helper.StringHelper;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeSpec;
import io.prometheus.client.Summary;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.lang.model.element.Modifier;

public class SummaryGenerator implements Generator {

  private static final String SUMMARY_BASE_NAME = "Summary";

  @Override
  public String getBaseName() {
    return SUMMARY_BASE_NAME;
  }

  @Override
  public String getTargetClassName(int totalLabels) {
    return getBaseName() + "With" + Util.inflectLabel(totalLabels);
  }

  @Override
  public Type getPrometheusClass() {
    return Summary.class;
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

    Builder constructorMethod2 = MethodSpec.constructorBuilder()
        .addModifiers(Modifier.PUBLIC)
        .addParameter(Summary.class, "summaryObject");

    constructorMethod2.addStatement("this.summary = summaryObject");
    methods.add(constructorMethod2.build());

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

  private Collection<? extends MethodSpec> getProxyMethods(int totalLabels) {
    return Arrays.asList(
        Util.proxyMethod(this, "observe", double.class, "val", totalLabels),
        Util.proxyMethod(this,  Summary.Timer.class,"startTimer", totalLabels)
    );
  }

  public void buildBaseClass(int maxLabelCount) {
    String className = getBaseName();

    List<MethodSpec> methods = new ArrayList<MethodSpec>();
    File targetFile = Util.getDefaultTarget();

    for (int labelCount = 0; labelCount <= maxLabelCount; labelCount++) {

      ClassName klazzReturns = ClassName
          .get("com.safeprometheus", getTargetClassName(labelCount));

      Builder methodCreateWith = MethodSpec.methodBuilder("buildWith" + Util.inflectLabel(labelCount) )
          .addModifiers(Modifier.PUBLIC)
          .addStatement("this.builder.name(metricName)")
          .addStatement("this.builder.help(help)")
          .addParameter(String.class, "metricName")
          .addParameter(String.class, "help")
          .returns(klazzReturns);

      List<String> labelNames = new ArrayList<>();

      for (int i = 0; i < labelCount; i++) {
        String labelNameI = "labelName" + (i + 1);
        methodCreateWith.addParameter(ClassName.get(String.class), labelNameI);
        labelNames.add(labelNameI);
      }

      if (labelCount > 0) {
        methodCreateWith.addStatement("this.builder.labelNames(" + String.join(", ", labelNames) + ")");
      }

      methodCreateWith.addStatement("return new " + getTargetClassName(labelCount) + "(builder.create().register())");

      methods.add(methodCreateWith.build());
    }

    MethodSpec builderMethod = MethodSpec.methodBuilder("builder")
        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
        .returns(getBaseKlazzTarget())
        .addStatement("return new Safe" + className + "()")
        .build();

    methods.add(builderMethod);

    FieldSpec builderField = FieldSpec.builder(Summary.Builder.class, "builder", Modifier.PRIVATE)
        .build();

    MethodSpec quantileMethod = MethodSpec.methodBuilder("quantile")
        .addModifiers(Modifier.PUBLIC)
        .addParameter(double.class, "quantile")
        .addParameter(double.class, "err")
        .addStatement("this.builder.quantile(quantile, err)")
        .addStatement("return this")
        .returns(getBaseKlazzTarget())
        .build();

    methods.add(quantileMethod);

    TypeSpec baseKlazz = TypeSpec.classBuilder("Safe" + className)
        .addMethods(methods)
        .addModifiers(Modifier.PUBLIC)
        .addField(builderField)
        .build();

    JavaFile base = JavaFile.builder("com.safeprometheus.base", baseKlazz).build();

    try {
      base.writeTo(targetFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private ClassName getBaseKlazzTarget() {
    return ClassName.get("com.safeprometheus.base", "Safe" + getBaseName());
  }
}