package com.github.huifer.user.link.persistence;
import com.github.huifer.user.link.servlet.MidDeptPostEntityCreateRequest;
import com.github.huifer.user.link.entity.MidDeptPostEntity;
import com.github.huifer.user.servlet.PostEntityResponse;
import com.github.huifer.user.servlet.DeptEntityResponse;
import java.util.List;


public interface MidDeptPostEntityPersistenceService {

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
