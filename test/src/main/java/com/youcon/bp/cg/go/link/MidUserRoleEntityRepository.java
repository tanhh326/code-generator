package com.youcon.bp.cg.go.link;


import com.youcon.bp.cg.go.link.MidUserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;




@Repository
public interface MidUserRoleEntityRepository  extends JpaRepository<MidUserRoleEntity, Long>, JpaSpecificationExecutor<MidUserRoleEntity> {

}
