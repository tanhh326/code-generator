package com.github.huifer.gn.core;

public enum CommonLineEnums {
  TABLE(1),
  LINK(2),
  ;
  private int code;

  CommonLineEnums(int code) {
    this.code = code;
  }

  public static CommonLineEnums conv(int code) {

    for (CommonLineEnums value : values()) {
      if (value.code ==code) {
        return value;
      }
    }
    throw new RuntimeException("无法正常使用");
  }
}
