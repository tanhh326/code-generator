package ${pkg};

<#list  imports as impt>
import ${impt};
</#list>

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@io.swagger.v3.oas.annotations.media.Schema(description = "${tableDesc}创建参数")
public class ${entityName}CreateRequest {


<#list  fields as field>
    /**
     * ${field.fieldDesc}
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="${field.fieldDesc}")
    private ${field.fieldType}  ${field.fieldName};
</#list>





}