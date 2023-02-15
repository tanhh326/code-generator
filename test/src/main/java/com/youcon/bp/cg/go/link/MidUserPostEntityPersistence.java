package com.youcon.bp.cg.go.link;
import com.youcon.bp.cg.go.link.MidUserPostEntity;
import com.youcon.bp.cg.go.PostEntityResponse;
import com.youcon.bp.cg.go.UserEntityResponse;
import java.util.List;


public interface MidUserPostEntityPersistence {

    /**
     * 建立关系
     */
    void bind(MidUserPostEntityCreateRequest request);

    /**
     * 删除关系
     */
    void unBind(MidUserPostEntityCreateRequest request);

    /**
     * 根据左侧id查询查询右侧实体
     */
    List<PostEntityResponse> findByLeftIds(MidUserPostEntityCreateRequest request);

    /**
     * 根据右侧id查询左侧实体
     */
    List<UserEntityResponse> findByRightIds(MidUserPostEntityCreateRequest request);

    /**
    * 根据左侧id和右侧id联合查询左侧实体
    */
    List<UserEntityResponse> findLeftAndRightWithUserEntity(MidUserPostEntityCreateRequest request);

    /**
     * 根据左侧id和右侧id联合查询右侧实体
     */
    List<PostEntityResponse> findLeftAndRightWithPostEntity(MidUserPostEntityCreateRequest request);
}
