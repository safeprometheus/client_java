package com.github.safeprometheus.generator;

import com.github.safeprometheus.helper.StringHelper;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeSpec;
import io.prometheus.client.Histogram;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.lang.model.element.Modifier;

public class HistogramGenerator implements  Generator{

  private static final String HISTOGRAM_BASE_NAME = "Histogram";

  @Override
  public String getBaseName() {
    return HISTOGRAM_BASE_NAME;
  }

  @Override
  public String getTargetClassName(int totalLabels) {
    return getBaseName() + "With" + Util.inflectLabel(totalLabels);
  }

  @Override
  public Type getPrometheusClass() {
    return Histogram.class;
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
        .addParameter(Histogram.class, "histogramObject");

    constructorMethod2.addStatement("this.histogram = histogramObject");
    methods.add(constructorMethod2.build());


    methods.addAll(getProxyMethods(totalLabels));

    FieldSpec gaugeField = FieldSpec.builder(getPrometheusClass(), getBaseName().toLowerCase(), Modifier.PROTECTED, Modifier.FINAL).build();

    TypeSpec klazz = TypeSpec.classBuilder(getTargetClassName(totalLabels))
        .addModifiers(Modifier.PUBLIC)
        .addMethods(methods)
        .addField(gaugeField)
        .build();
    JavaFile result = JavaFile.builder("com.github.safeprometheus", klazz)
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
        Util.proxyMethod(this,  Histogram.Timer.class,"startTimer", totalLabels)
    );
  }

  public void buildBaseClass(int maxLabelCount) {
    String className = getBaseName();

    List<MethodSpec> methods = new ArrayList<MethodSpec>();
    File targetFile = Util.getDefaultTarget();

    for (int labelCount = 0; labelCount <= maxLabelCount; labelCount++) {

      ClassName klazzReturns = ClassName
          .get("com.github.safeprometheus", getTargetClassName(labelCount));

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

    FieldSpec builderField = FieldSpec.builder(Histogram.Builder.class, "builder", Modifier.PRIVATE)
        .initializer("new Histogram.Builder()")
        .build();

    MethodSpec bucketsMethod = MethodSpec.methodBuilder("buckets")
        .addModifiers(Modifier.PUBLIC)
        .addParameter(ArrayTypeName.of(double.class), "buckets")
        .varargs()
        .addStatement("this.builder.buckets(buckets)")
        .addStatement("return this")
        .returns(getBaseKlazzTarget())
        .build();

    methods.add(bucketsMethod);

    TypeSpec baseKlazz = TypeSpec.classBuilder("Safe" + className)
        .addMethods(methods)
        .addModifiers(Modifier.PUBLIC)
        .addField(builderField)
        .build();

    JavaFile base = JavaFile.builder("com.github.safeprometheus.base", baseKlazz).build();

    try {
      base.writeTo(targetFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  private ClassName getBaseKlazzTarget() {
    return ClassName.get( "com.github.safeprometheus.base", "Safe" + getBaseName());
  }
}
