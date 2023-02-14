package com.github.huifer.gn.core;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class JavaProperties {

  private final String commomPKG;
  // 包名
  private final String pkg;
  // 类名
  private final String entityName;
  // 属性集合  需要改写 equals hash 保证名字可不重复 类型可重复
  private final Set<Field> fields = new LinkedHashSet<>();
  // 导入类的不重复集合
  private final Set<String> imports = new LinkedHashSet<>();
  private final String tableDesc;
  private final String tableName;

  public JavaProperties(String entityName, String tableName, String tableDesc, String pkg,
      String commomPKG) {
    this.tableDesc = tableDesc;
    this.tableName = tableName;
    this.entityName = entityName;
    this.pkg = pkg;
    this.commomPKG = commomPKG;
  }

  public String getCommomPKG() {
    return commomPKG;
  }

  public String getTableDesc() {
    return tableDesc;
  }

  public void addField(Class<?> type, String fieldName, String desc, boolean range) {

    // 处理 java.lang
    final String pattern = "java.lang";
    String fieldType = type.getName();
    if (!fieldType.startsWith(pattern)) {
      // 处理导包
      imports.add(fieldType);
    }
    Field field = new Field();
    // 处理成员属性的格式
    int i = fieldType.lastIndexOf(".");
    field.setRange(range);
    field.setFieldType(fieldType.substring(i + 1));
    field.setFieldName(fieldName);
    field.setFieldDesc(desc);
    fields.add(field);
  }

  public void addField(Class<?> type, String fieldName, String desc, boolean range, String l,
      String r) {

    // 处理 java.lang
    final String pattern = "java.lang";
    String fieldType = type.getName();
    if (!fieldType.startsWith(pattern)) {
      // 处理导包
      imports.add(fieldType);
    }
    Field field = new Field();
    // 处理成员属性的格式
    int i = fieldType.lastIndexOf(".");
    field.setRange(range);
    field.setLeft(fieldName.equals(l));
    field.setLeftName(l);
    field.setRightName(r);
    field.setFieldType(fieldType.substring(i + 1));
    field.setFieldName(fieldName);
    field.setFieldDesc(desc);
    fields.add(field);
    leftAndRight = new LeftAndRight();
    leftAndRight.setLeftName(l);
    leftAndRight.setRightName(r);
  }

  public String getPkg() {
    return pkg;
  }


  public String getEntityName() {
    return entityName;
  }

  public String getTableName() {
    return tableName;
  }

  public Set<Field> getFields() {
    return fields;
  }

  public Set<String> getImports() {
    return imports;
  }


  public LeftAndRight leftAndRight;

  public LeftAndRight getLeftAndRight() {
    return leftAndRight;
  }

  public void setLeftAndRight(LeftAndRight leftAndRight) {
    this.leftAndRight = leftAndRight;
  }

  public static class LeftAndRight{
    private String leftName;
    private String rightName;

    public String getLeftName() {
      return leftName;
    }

    public void setLeftName(String leftName) {
      this.leftName = leftName;
    }

    public String getRightName() {
      return rightName;
    }

    public void setRightName(String rightName) {
      this.rightName = rightName;
    }
  }

  /**
   * 成员属性封装对象.
   */
  public static class Field {

    // 成员属性类型
    private String fieldType;
    // 成员属性名称
    private String fieldName;
    private String fieldDesc;
    private boolean range;

    private boolean left;
    private String leftName;
    private String rightName;

    public boolean isLeft() {
      return left;
    }

    public void setLeft(boolean left) {
      this.left = left;
    }

    public String getLeftName() {
      return leftName;
    }

    public void setLeftName(String leftName) {
      this.leftName = leftName;
    }

    public String getRightName() {
      return rightName;
    }

    public void setRightName(String rightName) {
      this.rightName = rightName;
    }


    public String getFieldDesc() {
      return fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
      this.fieldDesc = fieldDesc;
    }

    public String getFieldType() {
      return fieldType;
    }

    public void setFieldType(String fieldType) {
      this.fieldType = fieldType;
    }

    public String getFieldName() {
      return fieldName;
    }

    public void setFieldName(String fieldName) {
      this.fieldName = fieldName;
    }

    public boolean isRange() {
      return range;
    }

    public void setRange(boolean range) {
      this.range = range;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Field field = (Field) o;
      return Objects.equals(fieldName, field.fieldName);
    }

    @Override
    public int hashCode() {
      return Objects.hash(fieldType, fieldName);
    }
  }

}