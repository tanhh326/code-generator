package ${pkg};

import ${commomPKG}.ResultResponse;
import ${lfEntPKG}.${leftAndRight.rightEntityName?cap_first}EntityResponse;
import ${lfEntPKG}.${leftAndRight.leftEntityName?cap_first}EntityResponse;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/${tableName}")
@Tag(name = "${tableDesc}")
public class ${entityName}Controller {

  @Autowired
  private ${entityName}Persistence ${entityName?uncap_first}Persistence;


  @PostMapping("/bind")
  @Operation(summary = "建立绑定关系")
  public ResultResponse<Boolean> bind(
      @RequestBody ${entityName}CreateRequest request
  ) {
    ${entityName?uncap_first}Persistence.bind(request);
    return ResultResponse.ok(true);
  }

  @PostMapping("/un_bind")
  @Operation(summary = "删除绑定关系")
  public ResultResponse<Boolean> unBind(
      @RequestBody ${entityName}CreateRequest request
  ) {
    ${entityName?uncap_first}Persistence.unBind(request);
    return ResultResponse.ok(true);
  }

  @PostMapping("/findByLeftIds")
  @Operation(summary = "根据左侧id查询查询右侧实体")
  public ResultResponse<List<${leftAndRight.rightEntityName?cap_first}EntityResponse>> findByLeftIds(
    @RequestBody ${entityName}CreateRequest request
  ) {
    return ResultResponse.ok(${entityName?uncap_first}Persistence.findByLeftIds(request));
  }

  @PostMapping("/findByRightIds")
  @Operation(summary = "根据右侧id查询左侧实体")
  public ResultResponse<List<${leftAndRight.leftEntityName?cap_first}EntityResponse>> findByRightIds(
    @RequestBody ${entityName}CreateRequest request
  ) {
    return ResultResponse.ok(${entityName?uncap_first}Persistence.findByRightIds(request));
  }


  @PostMapping("/findLeftAndRightWith${leftAndRight.leftEntityName?cap_first}")
  @Operation(summary="根据左侧id和右侧id联合查询左侧实体")
  public  ResultResponse<List<${leftAndRight.leftEntityName?cap_first}EntityResponse>> findLeftAndRightWith${leftAndRight.leftEntityName?cap_first}Entity(${entityName}CreateRequest request){
    return ResultResponse.ok(${entityName?uncap_first}Persistence.findLeftAndRightWith${leftAndRight.leftEntityName?cap_first}Entity(request));
  }


  @PostMapping("/findLeftAndRightWith${leftAndRight.rightEntityName?cap_first}")
  @Operation(summary="根据左侧id和右侧id联合查询右侧实体")
  public  ResultResponse<List<${leftAndRight.rightEntityName?cap_first}EntityResponse>> findLeftAndRightWith${leftAndRight.rightEntityName?cap_first}Entity(${entityName}CreateRequest request){
    return ResultResponse.ok(${entityName?uncap_first}Persistence.findLeftAndRightWith${leftAndRight.rightEntityName?cap_first}Entity(request));
  }

}
