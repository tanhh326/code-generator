package com.youcon.bp.cg.go;

import com.youcon.bp.cg.go.UserEntityPersistenceService;
import com.youcon.bp.cg.go.UserEntityQueryRequest;
import com.youcon.bp.cg.go.UserEntityRepository;
import com.youcon.bp.cg.go.UserEntityResponse;
import com.youcon.bp.cg.go.UserEntityUpdateRequest;
import com.youcon.bp.cg.go.UserEntityCreateRequest;
import com.youcon.bp.cg.PageAndSortRequest;
import com.youcon.bp.cg.ResultResponse;
import com.youcon.bp.cg.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@Tag(name = "用户")
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserEntityController {


  @Autowired
  private UserEntityPersistenceService userEntityPersistenceService;

  @Operation(summary = "添加用户")
  @PostMapping("/create")
  public ResultResponse<Long> create(
      @RequestBody UserEntityCreateRequest request
  ) {
    return ResultResponse.ok(userEntityPersistenceService.save(request));
  }

  @Operation(summary = "修改用户")
  @PostMapping("/update")
  public ResultResponse<Long> update(
      @RequestBody UserEntityUpdateRequest request
  ) {
    return ResultResponse.ok(userEntityPersistenceService.update(request));
  }

  @Operation(summary = "根据id查询")
  @GetMapping("/byId")
  public ResultResponse<UserEntityResponse> byId(
      Long id
  ) {
    return ResultResponse.ok(userEntityPersistenceService.byId(id));
  }

  @Operation(summary = "根据id集合查询")
  @GetMapping("/byIds")
  public ResultResponse<List<UserEntityResponse>> byIds(
      List<Long> ids
  ) {
    return ResultResponse.ok(userEntityPersistenceService.byIds(ids));
  }



  @Operation(summary = "用户列表")
  @GetMapping("/list")
  public ResultResponse<List<UserEntityResponse>> list(
      @RequestBody UserEntityQueryRequest request
  ) {
    return ResultResponse.ok(userEntityPersistenceService.list(request));
  }


  @Operation(summary = "用户分页")
  @GetMapping("/page")
  public ResultResponse<PageResponse<UserEntityResponse>> page(
      UserEntityQueryRequest request,
      PageAndSortRequest page
  ) {
    return ResultResponse.ok(userEntityPersistenceService.page(request, page));
  }


  @Operation(summary = "单个删除用户")
  @PostMapping("/delete")
  public ResultResponse<Boolean> delete(
      Long id
  ) {
    userEntityPersistenceService.delete(id);
    return ResultResponse.ok("删除成功");
  }

  @Operation(summary = "多个删除用户")
  @PostMapping("/deletes")
  public ResultResponse<Boolean> deletes(
      List<Long> ids
  ) {
    userEntityPersistenceService.deletes(ids);
    return ResultResponse.ok("删除成功");
  }


}