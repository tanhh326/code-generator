package ${pkg};

import java.util.List;

<#list  imports as impt>
import ${impt};

</#list>

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@io.swagger.v3.oas.annotations.media.Schema(description = "${tableDesc}查询参数")
public class ${entityName}QueryRequest {


<#list  fields as field>
    /**
     * ${field.fieldDesc}
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="${field.fieldDesc}")
    private ${field.fieldType}  ${field.fieldName};

    <#if field.range >
    private List<${field.fieldType}> ${field.fieldName}s;
    </#if>


</#list>





}