package com.youcon.bp.cg.go.link;

import com.youcon.bp.cg.ResultResponse;
import com.youcon.bp.cg.go.PostEntityResponse;
import com.youcon.bp.cg.go.UserEntityResponse;
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
@RequestMapping("/mid_user_post")
@Tag(name = "user_mid_post")
public class MidUserPostEntityController {

  @Autowired
  private MidUserPostEntityPersistence midUserPostEntityPersistence;


  @PostMapping("/bind")
  @Operation(summary = "建立绑定关系")
  public ResultResponse<Boolean> bind(
      @RequestBody MidUserPostEntityCreateRequest request
  ) {
    midUserPostEntityPersistence.bind(request);
    return ResultResponse.ok(true);
  }

  @PostMapping("/un_bind")
  @Operation(summary = "删除绑定关系")
  public ResultResponse<Boolean> unBind(
      @RequestBody MidUserPostEntityCreateRequest request
  ) {
    midUserPostEntityPersistence.unBind(request);
    return ResultResponse.ok(true);
  }

  @PostMapping("/findByLeftIds")
  @Operation(summary = "根据左侧id查询查询右侧实体")
  public ResultResponse<List<PostEntityResponse>> findByLeftIds(
    @RequestBody MidUserPostEntityCreateRequest request
  ) {
    return ResultResponse.ok(midUserPostEntityPersistence.findByLeftIds(request));
  }

  @PostMapping("/findByRightIds")
  @Operation(summary = "根据右侧id查询左侧实体")
  public ResultResponse<List<UserEntityResponse>> findByRightIds(
    @RequestBody MidUserPostEntityCreateRequest request
  ) {
    return ResultResponse.ok(midUserPostEntityPersistence.findByRightIds(request));
  }


  @PostMapping("/findLeftAndRightWithUser")
  @Operation(summary="根据左侧id和右侧id联合查询左侧实体")
  public  ResultResponse<List<UserEntityResponse>> findLeftAndRightWithUserEntity(MidUserPostEntityCreateRequest request){
    return ResultResponse.ok(midUserPostEntityPersistence.findLeftAndRightWithUserEntity(request));
  }


  @PostMapping("/findLeftAndRightWithPost")
  @Operation(summary="根据左侧id和右侧id联合查询右侧实体")
  public  ResultResponse<List<PostEntityResponse>> findLeftAndRightWithPostEntity(MidUserPostEntityCreateRequest request){
    return ResultResponse.ok(midUserPostEntityPersistence.findLeftAndRightWithPostEntity(request));
  }

}
