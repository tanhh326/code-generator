package com.youcon.bp.cg.go.link;


import com.youcon.bp.cg.go.link.MidUserDeptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;




@Repository
public interface MidUserDeptEntityRepository  extends JpaRepository<MidUserDeptEntity, Long>, JpaSpecificationExecutor<MidUserDeptEntity> {

}
