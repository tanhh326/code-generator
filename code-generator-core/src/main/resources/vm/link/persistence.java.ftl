<#function dashedToCamel(s)>
    <#return s
    ?replace('(^_+)|(_+$)', '', 'r')
    ?replace('\\_+(\\w)?', ' $1', 'r')
    ?replace('([A-Z])', ' $1', 'r')
    ?capitalize
    ?replace(' ' , '')
    ?uncap_first
    >
</#function>
package ${pkg};
import ${pkg}.${entityName};
import ${lfEntPKG}.${leftAndRight.rightEntityName?cap_first}EntityResponse;
import ${lfEntPKG}.${leftAndRight.leftEntityName?cap_first}EntityResponse;
import java.util.List;


public interface ${entityName}Persistence {

    /**
     * 建立关系
     */
    void bind(${entityName}CreateRequest request);

    /**
     * 删除关系
     */
    void unBind(${entityName}CreateRequest request);

    /**
     * 根据左侧id查询查询右侧实体
     */
    List<${leftAndRight.rightEntityName?cap_first}EntityResponse> findByLeftIds(${entityName}CreateRequest request);

    /**
     * 根据右侧id查询左侧实体
     */
    List<${leftAndRight.leftEntityName?cap_first}EntityResponse> findByRightIds(${entityName}CreateRequest request);

    /**
    * 根据左侧id和右侧id联合查询左侧实体
    */
    List<${leftAndRight.leftEntityName?cap_first}EntityResponse> findLeftAndRightWith${leftAndRight.leftEntityName?cap_first}Entity(${entityName}CreateRequest request);

    /**
     * 根据左侧id和右侧id联合查询右侧实体
     */
    List<${leftAndRight.rightEntityName?cap_first}EntityResponse> findLeftAndRightWith${leftAndRight.rightEntityName?cap_first}Entity(${entityName}CreateRequest request);
}
