package ${pkg};

<#list  imports as impt>
import ${impt};
</#list>

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@io.swagger.v3.oas.annotations.media.Schema(description = "${tableDesc}修改参数")
public class ${entityName}UpdateRequest {


<#list  fields as field>
    /**
     * ${field.fieldDesc}
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="${field.fieldDesc}")
    private ${field.fieldType}  ${field.fieldName};
</#list>




    @io.swagger.v3.oas.annotations.media.Schema(description="主键")
    private Long id;



}