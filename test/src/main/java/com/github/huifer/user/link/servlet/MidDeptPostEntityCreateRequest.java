package com.github.huifer.user.link.servlet;


import java.util.List;
import lombok.Data;

@Data
@io.swagger.v3.oas.annotations.media.Schema(description = " dept_mid_post 创建或删除")
public class MidDeptPostEntityCreateRequest {

  private List<Long> deptIds;
  private List<Long> postIds;


}