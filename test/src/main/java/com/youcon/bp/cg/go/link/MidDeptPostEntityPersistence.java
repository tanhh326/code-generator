package com.youcon.bp.cg.go.link;
import com.youcon.bp.cg.go.module.servlet.PostEntityResponse;
import com.youcon.bp.cg.go.module.servlet.DeptEntityResponse;
import java.util.List;


public interface MidDeptPostEntityPersistence {

    /**
     * 建立关系
     */
    void bind(MidDeptPostEntityCreateRequest request);

    /**
     * 删除关系
     */
    void unBind(MidDeptPostEntityCreateRequest request);

    /**
     * 根据左侧id查询查询右侧实体
     */
    List<PostEntityResponse> findByLeftIds(MidDeptPostEntityCreateRequest request);

    /**
     * 根据右侧id查询左侧实体
     */
    List<DeptEntityResponse> findByRightIds(MidDeptPostEntityCreateRequest request);

    /**
    * 根据左侧id和右侧id联合查询左侧实体
    */
    List<DeptEntityResponse> findLeftAndRightWithDeptEntity(MidDeptPostEntityCreateRequest request);

    /**
     * 根据左侧id和右侧id联合查询右侧实体
     */
    List<PostEntityResponse> findLeftAndRightWithPostEntity(MidDeptPostEntityCreateRequest request);
}
