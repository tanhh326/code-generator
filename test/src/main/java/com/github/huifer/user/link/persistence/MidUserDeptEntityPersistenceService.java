package com.github.huifer.user.link.persistence;
import com.github.huifer.user.link.servlet.MidUserDeptEntityCreateRequest;
import com.github.huifer.user.link.entity.MidUserDeptEntity;
import com.github.huifer.user.servlet.DeptEntityResponse;
import com.github.huifer.user.servlet.UserEntityResponse;
import java.util.List;


public interface MidUserDeptEntityPersistenceService {

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
