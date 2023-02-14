package ${pkg};


import ${commomPKG}.PageAndSortRequest;
import ${commomPKG}.PageResponse;
import java.util.List;

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

    /**
    * 批量删除
    */
    void deletes(List<Long> ids);

    /**
     * 返回列表
     */
    List<${entityName}Response> list(${entityName}QueryRequest request);

    /**
     * 分页
     */
    PageResponse<${entityName}Response> page(${entityName}QueryRequest request, PageAndSortRequest page);

}
