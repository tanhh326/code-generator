package com.youcon.bp.cg.go.service;


import com.youcon.bp.cg.PageAndSortRequest;
import com.youcon.bp.cg.PageResponse;
import com.youcon.bp.cg.go.servlet.PostEntityCreateRequest;
import com.youcon.bp.cg.go.servlet.PostEntityQueryRequest;
import com.youcon.bp.cg.go.servlet.PostEntityResponse;
import com.youcon.bp.cg.go.servlet.PostEntityUpdateRequest;
import java.util.List;

/**
 * 持久层交互
 **/
public interface PostEntityPersistenceService  {

    /**
     * 创建
     */
    Long save(PostEntityCreateRequest request);

    /**
     * 修改
     */
    Long update(PostEntityUpdateRequest request);

    /**
     * 删除
     */
    void delete(Long id);

    /**
     * 根据id查询
     */
    PostEntityResponse byId(Long id);


    /**
    * 根据id集合查询
    */
    List<PostEntityResponse> byIds(List<Long> ids);

    /**
    * 批量删除
    */
    void deletes(List<Long> ids);

    /**
     * 返回列表
     */
    List<PostEntityResponse> list(PostEntityQueryRequest request);


    /**
     * 分页
     */
    PageResponse<PostEntityResponse> page(PostEntityQueryRequest request, PageAndSortRequest page);






}
