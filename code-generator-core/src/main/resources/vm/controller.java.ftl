package ${pkg};

import ${pkg}.${entityName}PersistenceService;
import ${pkg}.${entityName}QueryRequest;
import ${pkg}.${entityName}Repository;
import ${pkg}.${entityName}Response;
import ${pkg}.${entityName}UpdateRequest;
import ${pkg}.${entityName}CreateRequest;
import ${commomPKG}.PageAndSortRequest;
import ${commomPKG}.ResultResponse;
import ${commomPKG}.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "${tableDesc}")
@RestController
@RequestMapping("/${tableName}")
public class ${entityName}Controller {


  @Autowired
  private ${entityName}PersistenceService ${entityName?uncap_first}PersistenceService;

  @Operation(summary = "添加${tableDesc}")
  @PostMapping("/create")
  public ResultResponse<Long> create(
      @RequestBody ${entityName}CreateRequest request
  ) {
    return ResultResponse.ok(${entityName?uncap_first}PersistenceService.save(request));
  }

  @Operation(summary = "修改${tableDesc}")
  @PostMapping("/update")
  public ResultResponse<Long> update(
      @RequestBody ${entityName}UpdateRequest request
  ) {
    return ResultResponse.ok(${entityName?uncap_first}PersistenceService.update(request));
  }

  @Operation(summary = "${tableDesc}列表")
  @GetMapping("/list")
  public ResultResponse<List<${entityName}Response>> list(
      @RequestBody ${entityName}QueryRequest request
  ) {
    return ResultResponse.ok(${entityName?uncap_first}PersistenceService.list(request));
  }

  @Operation(summary = "${tableDesc}分页")
  @GetMapping("/page")
  public ResultResponse<PageResponse<${entityName}Response>> page(
      ${entityName}QueryRequest request,
      PageAndSortRequest page
  ) {
    return ResultResponse.ok(${entityName?uncap_first}PersistenceService.page(request, page));
  }


  @Operation(summary = "单个删除${tableDesc}")
  @PostMapping("/delete")
  public ResultResponse<Boolean> delete(
      Long id
  ) {
    ${entityName?uncap_first}PersistenceService.delete(id);
    return ResultResponse.ok("删除成功");
  }

  @Operation(summary = "单个删除${tableDesc}")
  @PostMapping("/deletes")
  public ResultResponse<Boolean> deletes(
      List<Long> ids
  ) {
    ${entityName?uncap_first}PersistenceService.deletes(ids);
    return ResultResponse.ok("删除成功");
  }

<#if hasFk>
<#list  forinKeyList as fk>
  @Operation(summary = "根据${fk.fieldDesc}查询${tableDesc}")
  @GetMapping("/findBy${fk.fkName?cap_first}Id")
  public  ResultResponse<List<${entityName}Response>> findBy${fk.fkName?cap_first}Id(${fk.fieldType} ${fk.fieldName}){
    return ResultResponse.ok(${entityName?uncap_first}PersistenceService.findBy${fk.fkName?cap_first}Id(${fk.fieldName}));
  }

  @Operation(summary = "根据${fk.fieldDesc}查询${tableDesc}集合")
  @GetMapping("/findBy${fk.fkName?cap_first}Ids")
  public ResultResponse<List<${entityName}Response>> findBy${fk.fkName?cap_first}Ids(List<${fk.fieldType}> ${fk.fieldName}s){
    return ResultResponse.ok(${entityName?uncap_first}PersistenceService.findBy${fk.fkName?cap_first}Ids(${fk.fieldName}s));
  }

</#list>
</#if>
}