package com.youcon.bp.cg.go;

import com.youcon.bp.cg.go.PostEntityPersistenceService;
import com.youcon.bp.cg.go.PostEntityQueryRequest;
import com.youcon.bp.cg.go.PostEntityRepository;
import com.youcon.bp.cg.go.PostEntityResponse;
import com.youcon.bp.cg.go.PostEntityUpdateRequest;
import com.youcon.bp.cg.go.PostEntityCreateRequest;
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

@Tag(name = "岗位")
@RestController
@RequestMapping("/post")
public class PostEntityController {


  @Autowired
  private PostEntityPersistenceService postEntityPersistenceService;

  @Operation(summary = "添加岗位")
  @PostMapping("/create")
  public ResultResponse<Long> create(
      @RequestBody PostEntityCreateRequest request
  ) {
    return ResultResponse.ok(postEntityPersistenceService.save(request));
  }

  @Operation(summary = "修改岗位")
  @PostMapping("/update")
  public ResultResponse<Long> update(
      @RequestBody PostEntityUpdateRequest request
  ) {
    return ResultResponse.ok(postEntityPersistenceService.update(request));
  }

  @Operation(summary = "根据id查询")
  @GetMapping("/byId")
  public ResultResponse<PostEntityResponse> byId(
      Long id
  ) {
    return ResultResponse.ok(postEntityPersistenceService.byId(id));
  }

  @Operation(summary = "根据id集合查询")
  @GetMapping("/byIds")
  public ResultResponse<List<PostEntityResponse>> byIds(
      List<Long> ids
  ) {
    return ResultResponse.ok(postEntityPersistenceService.byIds(ids));
  }



  @Operation(summary = "岗位列表")
  @GetMapping("/list")
  public ResultResponse<List<PostEntityResponse>> list(
      @RequestBody PostEntityQueryRequest request
  ) {
    return ResultResponse.ok(postEntityPersistenceService.list(request));
  }


  @Operation(summary = "岗位分页")
  @GetMapping("/page")
  public ResultResponse<PageResponse<PostEntityResponse>> page(
      PostEntityQueryRequest request,
      PageAndSortRequest page
  ) {
    return ResultResponse.ok(postEntityPersistenceService.page(request, page));
  }


  @Operation(summary = "单个删除岗位")
  @PostMapping("/delete")
  public ResultResponse<Boolean> delete(
      Long id
  ) {
    postEntityPersistenceService.delete(id);
    return ResultResponse.ok("删除成功");
  }

  @Operation(summary = "多个删除岗位")
  @PostMapping("/deletes")
  public ResultResponse<Boolean> deletes(
      List<Long> ids
  ) {
    postEntityPersistenceService.deletes(ids);
    return ResultResponse.ok("删除成功");
  }


}