package com.github.huifer.user.link.servlet;


import java.util.List;
import lombok.Data;

@Data
@io.swagger.v3.oas.annotations.media.Schema(description = " user_mid_dept 创建或删除")
public class MidUserDeptEntityCreateRequest {

  private List<Long> userIds;
  private List<Long> deptIds;


}