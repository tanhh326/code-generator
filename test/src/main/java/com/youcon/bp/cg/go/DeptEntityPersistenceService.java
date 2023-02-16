package com.youcon.bp.cg.go;


import com.youcon.bp.cg.PageAndSortRequest;
import com.youcon.bp.cg.PageResponse;
import java.util.List;
import cn.hutool.core.lang.tree.Tree;

/**
 * 持久层交互
 **/
public interface DeptEntityPersistenceService  {

    /**
     * 创建
     */
    Long save(DeptEntityCreateRequest request);

    /**
     * 修改
     */
    Long update(DeptEntityUpdateRequest request);

    /**
     * 删除
     */
    void delete(Long id);

    /**
     * 根据id查询
     */
    DeptEntityResponse byId(Long id);


    /**
    * 根据id集合查询
    */
    List<DeptEntityResponse> byIds(List<Long> ids);
    /**
    * 根据id集合查询树
    */
    List<Tree<Object>> byIdsTree(List<Long> ids);

    /**
    * 批量删除
    */
    void deletes(List<Long> ids);

    /**
     * 返回列表
     */
    List<DeptEntityResponse> list(DeptEntityQueryRequest request);

    /**
    * 根据查询条件返回树
    */
    List<Tree<Object>> listTree(DeptEntityQueryRequest request);

    /**
     * 分页
     */
    PageResponse<DeptEntityResponse> page(DeptEntityQueryRequest request, PageAndSortRequest page);




    /**
     * 根据单位id查询部门集合
     **/
    List<DeptEntityResponse> findByCompanyId(Long companyId);
  /**
  * 根据单位id查询部门树
  **/
  List<Tree<Object>> findByCompanyIdTree(Long companyId);
    /**
    * 根据单位id查询部门集合
    **/
    List<DeptEntityResponse> findByCompanyIds(List<Long> companyIds);
    /**
    * 根据单位id查询部门树
    **/
    List<Tree<Object>> findByCompanyIdsTree(List<Long> companyIds);

    /**
     * 查询树
     */
    List<Tree<Object>> tree();

    /**
     * 构造树
     */
    List<Tree<Object>> tree(List<DeptEntity> all);


}
