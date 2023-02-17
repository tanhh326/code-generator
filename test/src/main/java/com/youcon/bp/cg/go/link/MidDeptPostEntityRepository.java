package com.youcon.bp.cg.go.link;


import com.youcon.bp.cg.go.link.MidDeptPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;




@Repository
public interface MidDeptPostEntityRepository  extends JpaRepository<MidDeptPostEntity, Long>, JpaSpecificationExecutor<MidDeptPostEntity> {

}
