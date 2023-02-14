package ${pkg};


import java.util.List;
import lombok.Data;

@Data
@io.swagger.v3.oas.annotations.media.Schema(description = " ${tableDesc} 创建或删除")
public class ${entityName}CreateRequest {

<#list  fields as field>
  private List<${field.fieldType}> ${field.fieldName}s;
</#list>


}