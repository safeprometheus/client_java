package com.github.safeprometheus.generator;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;

public class Util {
  public static File getDefaultTarget() {
    return new File("src/main/java");
  }

  public static String inflectLabel(int labelCount) {
    if (labelCount == 0) {
      return "NoLabel";
    } else if (labelCount == 1) {
      return "1Label";
    } else if (labelCount < 10) {
      return labelCount + "Labels";
    }

    throw new IllegalArgumentException("Not handled: label count is " + labelCount);
  }

  public static String buildSafeConstructor(Generator g, String targetClassName,
      int labelCount) {
    ArrayList<String> parameters = new ArrayList<String>();

    parameters.add("metricName");
    parameters.add("help");

    for (int i = 0; i < labelCount; i++) {
      parameters.add("labelName" + (i+1));
    }

    return "return new " + g.getTargetClassName(labelCount) + "(" + String.join(", ", parameters) + ")";
  }

  public static String metricBuildStatement(String metricType, int labelCount) {
    StringBuilder sb = new StringBuilder();
    sb.append("this.");
    sb.append(metricType.toLowerCase());
    sb.append(" = ");
    sb.append(metricType);
    sb.append(".build(metricName, metricHelp)");

    if (labelCount > 0) {
      sb.append(".labelNames(");
      for (int i = 0; i < labelCount; i++) {
        if (i > 0) {
          sb.append(", ");
        }
        sb.append("labelName");
        sb.append(i + 1);
      }
      sb.append(")");
    }
    sb.append(".create().register()");
    return sb.toString();
  }


  public static MethodSpec proxyMethod(Generator g, String methodName, int labelCount) {
    String labelStatements = buildLabelStatement(labelCount);

    List<String> args = new ArrayList<>();

    args.add(g.getBaseName().toLowerCase());
    args.add(methodName);

    Builder b = MethodSpec.methodBuilder(methodName)
        .addModifiers(Modifier.PUBLIC)
        .addStatement("this.$L" + labelStatements + ".$L()", args.toArray());

    for (int i = 0; i < labelCount; i++) {
      b.addParameter(String.class, "labelValue" + (i+1));
    }

    return b.build();
  }

  public static MethodSpec proxyMethod(Generator g, String methodName, Type param1Type, String param1Name, int labelCount) {
    String labelStatements = buildLabelStatement(labelCount);

    List<String> args = new ArrayList<>();

    args.add(g.getBaseName().toLowerCase());
    args.add(methodName);
    args.add(param1Name);

    Builder b = MethodSpec.methodBuilder(methodName)
        .addModifiers(Modifier.PUBLIC)
        .addStatement("this.$L" + labelStatements + ".$L($L)", args.toArray());

    b.addParameter(param1Type, param1Name);
    for (int i = 0; i < labelCount; i++) {
      b.addParameter(String.class, "labelValue" + (i+1));
    }

    return b.build();
  }

  public static MethodSpec proxyMethod(Generator g, Type returnType, String methodName, int labelCount) {
    String labelStatements = buildLabelStatement(labelCount);

    List<String> args = new ArrayList<>();

    args.add(g.getBaseName().toLowerCase());
    args.add(methodName);

    Builder b = MethodSpec.methodBuilder(methodName)
        .addModifiers(Modifier.PUBLIC)
        .returns(returnType)
        .addStatement("return this.$L" + labelStatements.toString() + ".$L()", args.toArray());

    for (int i = 0; i < labelCount; i++) {
      b.addParameter(String.class, "labelValue" + (i+1));
    }

    return b.build();
  }

  public static String buildLabelStatement(int labelCount) {
    StringBuilder labelStatements = new StringBuilder();
    if (labelCount > 0) {
      labelStatements.append(".labels(");
      for (int i = 0; i < labelCount; i++) {
        if (i > 0) {
          labelStatements.append(", ");
        }
        labelStatements.append("nullSafe(labelValue");
        labelStatements.append(i + 1);
        labelStatements.append(")");
      }
      labelStatements.append(")");
    }
    return labelStatements.toString();
  }
}
