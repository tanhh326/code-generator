package com.github.huifer.gn.core;

import lombok.Data;

@Data
public class LinkTableInfo {

  public LinkTableInfo(String left, String right) {
    this.left = left;
    this.right = right;
  }


  public String getDesc() {
    return  left  +"_"+ "mid"+ "_" + right;
  }

  public String tableName(){
    return "mid_" + left + "_" + right;
  }

  public String getLeft() {
    return left;
  }

  public void setLeft(String left) {
    this.left = left;
  }

  public String getRight() {
    return right;
  }

  public void setRight(String right) {
    this.right = right;
  }

  private String left;
  private String right;

}
