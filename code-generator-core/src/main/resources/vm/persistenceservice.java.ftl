package ${pkg};


import ${commomPKG}.PageAndSortRequest;
import ${commomPKG}.PageResponse;
import java.util.List;
import cn.hutool.core.lang.tree.Tree;

/**
 * 持久层交互
 **/
public interface ${entityName}PersistenceService  {

    /**
     * 创建
     */
    Long save(${entityName}CreateRequest request);

    /**
     * 修改
     */
    Long update(${entityName}UpdateRequest request);

    /**
     * 删除
     */
    void delete(Long id);

    /**
     * 根据id查询
     */
    ${entityName}Response byId(Long id);


    /**
    * 根据id集合查询
    */
    List<${entityName}Response> byIds(List<Long> ids);
<#if pidField??>
    /**
    * 根据id集合查询树
    */
    List<Tree<Object>> byIdsTree(List<Long> ids);
</#if>

    /**
    * 批量删除
    */
    void deletes(List<Long> ids);

    /**
     * 返回列表
     */
    List<${entityName}Response> list(${entityName}QueryRequest request);

<#if pidField??>
    /**
    * 根据查询条件返回树
    */
    List<Tree<Object>> listTree(${entityName}QueryRequest request);
</#if>

    /**
     * 分页
     */
    PageResponse<${entityName}Response> page(${entityName}QueryRequest request, PageAndSortRequest page);




<#if forinKeyList?? && (forinKeyList?size>0)>
<#list  forinKeyList as fk>
    /**
     * 根据${fk.fieldDesc}查询${tableDesc}集合
     **/
    List<${entityName}Response> findBy${fk.fkName?cap_first}Id(${fk.fieldType} ${fk.fieldName});
<#if pidField??>
  /**
  * 根据${fk.fieldDesc}查询${tableDesc}树
  **/
  List<Tree<Object>> findBy${fk.fkName?cap_first}IdTree(${fk.fieldType} ${fk.fieldName});
</#if>
    /**
    * 根据${fk.fieldDesc}查询${tableDesc}集合
    **/
    List<${entityName}Response> findBy${fk.fkName?cap_first}Ids(List<${fk.fieldType}> ${fk.fieldName}s);
<#if pidField??>
    /**
    * 根据${fk.fieldDesc}查询${tableDesc}树
    **/
    List<Tree<Object>> findBy${fk.fkName?cap_first}IdsTree(${fk.fieldType} ${fk.fieldName}s);
</#if>
</#list>
</#if>

<#if pidField??>
    /**
     * 查询树
     */
    List<Tree<Object>> tree();

    /**
     * 构造树
     */
    List<Tree<Object>> tree(List<${entityName}> all);

</#if>

}
