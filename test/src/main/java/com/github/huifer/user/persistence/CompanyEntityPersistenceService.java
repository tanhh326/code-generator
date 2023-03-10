package com.github.huifer.user.persistence;


import com.youcon.bp.cg.PageAndSortRequest;
import com.youcon.bp.cg.PageResponse;
import java.util.List;
import cn.hutool.core.lang.tree.Tree;
import com.github.huifer.user.entity.CompanyEntity;
import com.github.huifer.user.servlet.CompanyEntityCreateRequest;
import com.github.huifer.user.servlet.CompanyEntityQueryRequest;
import com.github.huifer.user.servlet.CompanyEntityResponse;
import com.github.huifer.user.servlet.CompanyEntityUpdateRequest;

/**
 * 持久层交互
 **/
public interface CompanyEntityPersistenceService  {

    /**
     * 创建
     */
    Long save(CompanyEntityCreateRequest request);

    /**
     * 修改
     */
    Long update(CompanyEntityUpdateRequest request);

    /**
     * 删除
     */
    void delete(Long id);

    /**
     * 根据id查询
     */
    CompanyEntityResponse byId(Long id);


    /**
    * 根据id集合查询
    */
    List<CompanyEntityResponse> byIds(List<Long> ids);

    /**
    * 批量删除
    */
    void deletes(List<Long> ids);

    /**
     * 返回列表
     */
    List<CompanyEntityResponse> list(CompanyEntityQueryRequest request);


    /**
     * 分页
     */
    PageResponse<CompanyEntityResponse> page(CompanyEntityQueryRequest request, PageAndSortRequest page);






}
