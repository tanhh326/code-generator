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

    private boolean range;

    private boolean fk;

    private boolean pid;
  }


}
