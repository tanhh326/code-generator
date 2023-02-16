package com.github.huifer.gn.core;

import java.util.List;
import lombok.Data;

@Data
public class TableInfo {

  private String tableName;
  private String tableDesc;

  private boolean mid;
  private List<FieldInfo> fieldInfos;


  @Data
  public static class FieldInfo {

    private String fieldName;
    private String fieldDesc;
    private FieldType type;
    private boolean nul;

    /**
     * 是否允许范围查询
     */
    private boolean range;

    /**
     * 是否是外键
     */
    private boolean fk;

    /**
     * 是否是pid
     */
    private boolean pid;

    /**
     * 是否需要前端列表显示
     */
    private boolean tableShow;
  }


}
