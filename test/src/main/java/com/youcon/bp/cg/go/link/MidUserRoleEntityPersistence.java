package com.youcon.bp.cg.go.link;
import com.youcon.bp.cg.go.link.MidUserRoleEntity;


public interface MidUserRoleEntityPersistence {

    /**
    * 建立关系
    */
    void bind(MidUserRoleEntityCreateRequest request);

    /**
    * 删除关系
    */
    void unBind(MidUserRoleEntityCreateRequest request);

}
