package com.youcon.bp.cg.go.link;

import com.youcon.bp.cg.ResultResponse;
import com.youcon.bp.cg.go.PostEntityResponse;
import com.youcon.bp.cg.go.DeptEntityResponse;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mid_dept_post")
@Tag(name = "dept_mid_post")
public class MidDeptPostEntityController {

  @Autowired
  private MidDeptPostEntityPersistence midDeptPostEntityPersistence;


  @PostMapping("/bind")
  @Operation(summary = "建立绑定关系")
  public ResultResponse<Boolean> bind(
      @RequestBody MidDeptPostEntityCreateRequest request
  ) {
    midDeptPostEntityPersistence.bind(request);
    return ResultResponse.ok(true);
  }

  @PostMapping("/un_bind")
  @Operation(summary = "删除绑定关系")
  public ResultResponse<Boolean> unBind(
      @RequestBody MidDeptPostEntityCreateRequest request
  ) {
    midDeptPostEntityPersistence.unBind(request);
    return ResultResponse.ok(true);
  }

  @PostMapping("/findByLeftIds")
  @Operation(summary = "根据左侧id查询查询右侧实体")
  public ResultResponse<List<PostEntityResponse>> findByLeftIds(
    @RequestBody MidDeptPostEntityCreateRequest request
  ) {
    return ResultResponse.ok(midDeptPostEntityPersistence.findByLeftIds(request));
  }

  @PostMapping("/findByRightIds")
  @Operation(summary = "根据右侧id查询左侧实体")
  public ResultResponse<List<DeptEntityResponse>> findByRightIds(
    @RequestBody MidDeptPostEntityCreateRequest request
  ) {
    return ResultResponse.ok(midDeptPostEntityPersistence.findByRightIds(request));
  }


  @PostMapping("/findLeftAndRightWithDept")
  @Operation(summary="根据左侧id和右侧id联合查询左侧实体")
  public  ResultResponse<List<DeptEntityResponse>> findLeftAndRightWithDeptEntity(MidDeptPostEntityCreateRequest request){
    return ResultResponse.ok(midDeptPostEntityPersistence.findLeftAndRightWithDeptEntity(request));
  }


  @PostMapping("/findLeftAndRightWithPost")
  @Operation(summary="根据左侧id和右侧id联合查询右侧实体")
  public  ResultResponse<List<PostEntityResponse>> findLeftAndRightWithPostEntity(MidDeptPostEntityCreateRequest request){
    return ResultResponse.ok(midDeptPostEntityPersistence.findLeftAndRightWithPostEntity(request));
  }

}
