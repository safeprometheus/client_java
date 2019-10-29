package com.github.safeprometheus.helper;

public class StringHelper {
  public static String nullSafe(String object) {
    if (object == null) {
      return "null";
    } else if (object.length() == 0) {
      return "empty";
    }
    return object;
  }
}
