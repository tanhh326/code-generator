package com.github.huifer.user.link.controller;

import com.youcon.bp.cg.ResultResponse;
import com.github.huifer.user.link.persistence.MidUserDeptEntityPersistenceService;
import com.github.huifer.user.link.servlet.MidUserDeptEntityCreateRequest;

import com.github.huifer.user.servlet.DeptEntityResponse;
import com.github.huifer.user.servlet.UserEntityResponse;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin
@RequestMapping("/mid_user_dept")
@Tag(name = "user_mid_dept")
public class MidUserDeptEntityController {

  @Autowired
  private MidUserDeptEntityPersistenceService midUserDeptEntityPersistence;


  @PostMapping("/bind")
  @Operation(summary = "建立绑定关系")
  public ResultResponse<Boolean> bind(
      @RequestBody MidUserDeptEntityCreateRequest request
  ) {
    midUserDeptEntityPersistence.bind(request);
    return ResultResponse.ok(true);
  }

  @PostMapping("/un_bind")
  @Operation(summary = "删除绑定关系")
  public ResultResponse<Boolean> unBind(
      @RequestBody MidUserDeptEntityCreateRequest request
  ) {
    midUserDeptEntityPersistence.unBind(request);
    return ResultResponse.ok(true);
  }

  @PostMapping("/findByLeftIds")
  @Operation(summary = "根据左侧id查询查询右侧实体")
  public ResultResponse<List<DeptEntityResponse>> findByLeftIds(
    @RequestBody MidUserDeptEntityCreateRequest request
  ) {
    return ResultResponse.ok(midUserDeptEntityPersistence.findByLeftIds(request));
  }

  @PostMapping("/findByRightIds")
  @Operation(summary = "根据右侧id查询左侧实体")
  public ResultResponse<List<UserEntityResponse>> findByRightIds(
    @RequestBody MidUserDeptEntityCreateRequest request
  ) {
    return ResultResponse.ok(midUserDeptEntityPersistence.findByRightIds(request));
  }


  @PostMapping("/findLeftAndRightWithUser")
  @Operation(summary="根据左侧id和右侧id联合查询左侧实体")
  public  ResultResponse<List<UserEntityResponse>> findLeftAndRightWithUserEntity(MidUserDeptEntityCreateRequest request){
    return ResultResponse.ok(midUserDeptEntityPersistence.findLeftAndRightWithUserEntity(request));
  }


  @PostMapping("/findLeftAndRightWithDept")
  @Operation(summary="根据左侧id和右侧id联合查询右侧实体")
  public  ResultResponse<List<DeptEntityResponse>> findLeftAndRightWithDeptEntity(MidUserDeptEntityCreateRequest request){
    return ResultResponse.ok(midUserDeptEntityPersistence.findLeftAndRightWithDeptEntity(request));
  }

}
