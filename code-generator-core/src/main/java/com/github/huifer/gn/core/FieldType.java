package com.github.huifer.gn.core;

import static org.jooq.impl.SQLDataType.BIGINT;
import static org.jooq.impl.SQLDataType.DECIMAL;
import static org.jooq.impl.SQLDataType.LOCALDATE;
import static org.jooq.impl.SQLDataType.LOCALDATETIME;
import static org.jooq.impl.SQLDataType.VARCHAR;

import java.time.LocalDate;
import org.jooq.DataType;

public enum FieldType {
  BigDecimal(1, DECIMAL(18, 6), java.math.BigDecimal.class),
  Varchar(2, VARCHAR.length(255), String.class),
  LocalDateTime(3, LOCALDATETIME, java.time.LocalDateTime.class),
  Date(4, LOCALDATE, LocalDate.class),
  Long(4, BIGINT, Long.class),
  ;
  private int code;
  private DataType dt;

  private Class<?> clazz;

  FieldType(int code, DataType dt, Class<?> clazz) {
    this.code = code;
    this.dt = dt;
    this.clazz = clazz;
  }

  FieldType(int code) {
    this.code = code;
  }

  public static FieldType conv(int code) {
    for (FieldType value : values()) {
      if (value.code == code) {
        return value;
      }
    }
    throw new RuntimeException("异常转换类型");
  }

  public Class<?> getClazz() {
    return clazz;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public DataType getDt() {
    return dt;
  }

  public void setDt(DataType dt) {
    this.dt = dt;
  }
}
