package com.youcon.bp.cg.go.link;


import java.util.List;
import lombok.Data;

@Data
@io.swagger.v3.oas.annotations.media.Schema(description = " user_mid_role 创建或删除")
public class MidUserRoleEntityCreateRequest {

  private List<Long> userIds;
  private List<Long> roleIds;


}