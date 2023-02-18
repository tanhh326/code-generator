package com.github.huifer.user.link.persistence;
import com.github.huifer.user.link.servlet.MidUserPostEntityCreateRequest;
import com.github.huifer.user.link.entity.MidUserPostEntity;
import com.github.huifer.user.servlet.PostEntityResponse;
import com.github.huifer.user.servlet.UserEntityResponse;
import java.util.List;


public interface MidUserPostEntityPersistenceService {

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
