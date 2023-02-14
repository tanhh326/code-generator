package com.youcon.bp.cg.go;


import com.youcon.bp.cg.PageAndSortRequest;
import com.youcon.bp.cg.PageResponse;
import java.util.List;

/**
 * 持久层交互
 **/
public interface UserEntityPersistenceService  {

    /**
     * 创建
     */
    Long save(UserEntityCreateRequest request);

    /**
     * 修改
     */
    Long update(UserEntityUpdateRequest request);

    /**
     * 删除
     */
    void delete(Long id);

    /**
     * 根据id查询
     */
    UserEntityResponse byId(Long id);


    /**
    * 根据id集合查询
    */
    List<UserEntityResponse> byIds(List<Long> ids);

    /**
    * 批量删除
    */
    void deletes(List<Long> ids);

    /**
     * 返回列表
     */
    List<UserEntityResponse> list(UserEntityQueryRequest request);

    /**
     * 分页
     */
    PageResponse<UserEntityResponse> page(UserEntityQueryRequest request, PageAndSortRequest page);

}
