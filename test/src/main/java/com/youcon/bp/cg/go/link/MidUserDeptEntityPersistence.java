package com.youcon.bp.cg.go.link;
import com.youcon.bp.cg.go.module.servlet.DeptEntityResponse;
import com.youcon.bp.cg.go.module.servlet.UserEntityResponse;
import java.util.List;


public interface MidUserDeptEntityPersistence {

    /**
     * 建立关系
     */
    void bind(MidUserDeptEntityCreateRequest request);

    /**
     * 删除关系
     */
    void unBind(MidUserDeptEntityCreateRequest request);

    /**
     * 根据左侧id查询查询右侧实体
     */
    List<DeptEntityResponse> findByLeftIds(MidUserDeptEntityCreateRequest request);

    /**
     * 根据右侧id查询左侧实体
     */
    List<UserEntityResponse> findByRightIds(MidUserDeptEntityCreateRequest request);

    /**
    * 根据左侧id和右侧id联合查询左侧实体
    */
    List<UserEntityResponse> findLeftAndRightWithUserEntity(MidUserDeptEntityCreateRequest request);

    /**
     * 根据左侧id和右侧id联合查询右侧实体
     */
    List<DeptEntityResponse> findLeftAndRightWithDeptEntity(MidUserDeptEntityCreateRequest request);
}
