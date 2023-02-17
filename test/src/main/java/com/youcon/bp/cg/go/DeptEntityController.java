package com.youcon.bp.cg.go;

import cn.hutool.core.lang.tree.Tree;
import com.youcon.bp.cg.go.DeptEntityPersistenceService;
import com.youcon.bp.cg.go.DeptEntityQueryRequest;
import com.youcon.bp.cg.go.DeptEntityRepository;
import com.youcon.bp.cg.go.DeptEntityResponse;
import com.youcon.bp.cg.go.DeptEntityUpdateRequest;
import com.youcon.bp.cg.go.DeptEntityCreateRequest;
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

@Tag(name = "部门")
@CrossOrigin
@RestController
@RequestMapping("/dept")
public class DeptEntityController {


  @Autowired
  private DeptEntityPersistenceService deptEntityPersistenceService;

  @Operation(summary = "添加部门")
  @PostMapping("/create")
  public ResultResponse<Long> create(
      @RequestBody DeptEntityCreateRequest request
  ) {
    return ResultResponse.ok(deptEntityPersistenceService.save(request));
  }

  @Operation(summary = "修改部门")
  @PostMapping("/update")
  public ResultResponse<Long> update(
      @RequestBody DeptEntityUpdateRequest request
  ) {
    return ResultResponse.ok(deptEntityPersistenceService.update(request));
  }

  @Operation(summary = "根据id查询")
  @GetMapping("/byId")
  public ResultResponse<DeptEntityResponse> byId(
      Long id
  ) {
    return ResultResponse.ok(deptEntityPersistenceService.byId(id));
  }

  @Operation(summary = "根据id集合查询")
  @GetMapping("/byIds")
  public ResultResponse<List<DeptEntityResponse>> byIds(
      List<Long> ids
  ) {
    return ResultResponse.ok(deptEntityPersistenceService.byIds(ids));
  }


  @Operation(summary = "根据id集合查询（树结构）")
  @GetMapping("/byIds/tree")
  public ResultResponse<List<Tree<Object>>> byIdsTree(
      List<Long> ids
  ) {
    return ResultResponse.ok(deptEntityPersistenceService.byIdsTree(ids));
  }

  @Operation(summary = "部门列表")
  @GetMapping("/list")
  public ResultResponse<List<DeptEntityResponse>> list(
      @RequestBody DeptEntityQueryRequest request
  ) {
    return ResultResponse.ok(deptEntityPersistenceService.list(request));
  }

  @Operation(summary = "部门列表(树结构)")
  @GetMapping("/list/tree")
  public ResultResponse<List<Tree<Object>>> listTree(
      @RequestBody DeptEntityQueryRequest request
  ) {
    return ResultResponse.ok(deptEntityPersistenceService.listTree(request));
  }

  @Operation(summary = "部门分页")
  @GetMapping("/page")
  public ResultResponse<PageResponse<DeptEntityResponse>> page(
      DeptEntityQueryRequest request,
      PageAndSortRequest page
  ) {
    return ResultResponse.ok(deptEntityPersistenceService.page(request, page));
  }


  @Operation(summary = "单个删除部门")
  @PostMapping("/delete")
  public ResultResponse<Boolean> delete(
      Long id
  ) {
    deptEntityPersistenceService.delete(id);
    return ResultResponse.ok("删除成功");
  }

  @Operation(summary = "多个删除部门")
  @PostMapping("/deletes")
  public ResultResponse<Boolean> deletes(
      List<Long> ids
  ) {
    deptEntityPersistenceService.deletes(ids);
    return ResultResponse.ok("删除成功");
  }

  @Operation(summary = "根据单位id查询部门")
  @GetMapping("/findByCompanyId")
  public  ResultResponse<List<DeptEntityResponse>> findByCompanyId(Long companyId){
    return ResultResponse.ok(deptEntityPersistenceService.findByCompanyId(companyId));
  }

  @Operation(summary = "根据单位id查询部门(树结构)")
  @GetMapping("/findByCompanyId/tree")
  public  ResultResponse<List<Tree<Object>>> findByCompanyIdTree(Long companyId){
    return ResultResponse.ok(deptEntityPersistenceService.findByCompanyIdTree(companyId));
  }
  @Operation(summary = "根据单位id查询部门集合")
  @GetMapping("/findByCompanyIds")
  public ResultResponse<List<DeptEntityResponse>> findByCompanyIds(List<Long> companyIds){
    return ResultResponse.ok(deptEntityPersistenceService.findByCompanyIds(companyIds));
  }
  @Operation(summary = "根据单位id查询部门集合(树结构)")
  @GetMapping("/findByCompanyIds/tree")
  public ResultResponse<List<Tree<Object>>> findByCompanyIdsTree(List<Long> companyIds){
    return ResultResponse.ok(deptEntityPersistenceService.findByCompanyIdsTree(companyIds));
  }



  @Operation(summary = "全表树(慎重使用)")
  @GetMapping("/tree")
  public ResultResponse<List<Tree<Object>>> tree(){
    return ResultResponse.ok(deptEntityPersistenceService.tree());
  }


}