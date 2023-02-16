package com.youcon.bp.cg.go;

import com.youcon.bp.cg.go.CompanyEntityPersistenceService;
import com.youcon.bp.cg.go.CompanyEntityQueryRequest;
import com.youcon.bp.cg.go.CompanyEntityRepository;
import com.youcon.bp.cg.go.CompanyEntityResponse;
import com.youcon.bp.cg.go.CompanyEntityUpdateRequest;
import com.youcon.bp.cg.go.CompanyEntityCreateRequest;
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

@Tag(name = "单位")
@RestController
@RequestMapping("/company")
public class CompanyEntityController {


  @Autowired
  private CompanyEntityPersistenceService companyEntityPersistenceService;

  @Operation(summary = "添加单位")
  @PostMapping("/create")
  public ResultResponse<Long> create(
      @RequestBody CompanyEntityCreateRequest request
  ) {
    return ResultResponse.ok(companyEntityPersistenceService.save(request));
  }

  @Operation(summary = "修改单位")
  @PostMapping("/update")
  public ResultResponse<Long> update(
      @RequestBody CompanyEntityUpdateRequest request
  ) {
    return ResultResponse.ok(companyEntityPersistenceService.update(request));
  }

  @Operation(summary = "单位列表")
  @GetMapping("/list")
  public ResultResponse<List<CompanyEntityResponse>> list(
      @RequestBody CompanyEntityQueryRequest request
  ) {
    return ResultResponse.ok(companyEntityPersistenceService.list(request));
  }

  @Operation(summary = "单位分页")
  @GetMapping("/page")
  public ResultResponse<PageResponse<CompanyEntityResponse>> page(
      CompanyEntityQueryRequest request,
      PageAndSortRequest page
  ) {
    return ResultResponse.ok(companyEntityPersistenceService.page(request, page));
  }


  @Operation(summary = "单个删除单位")
  @PostMapping("/delete")
  public ResultResponse<Boolean> delete(
      Long id
  ) {
    companyEntityPersistenceService.delete(id);
    return ResultResponse.ok("删除成功");
  }

  @Operation(summary = "单个删除单位")
  @PostMapping("/deletes")
  public ResultResponse<Boolean> deletes(
      List<Long> ids
  ) {
    companyEntityPersistenceService.deletes(ids);
    return ResultResponse.ok("删除成功");
  }

}