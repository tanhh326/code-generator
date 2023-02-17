package com.youcon.bp.cg.go.link;


import com.youcon.bp.cg.go.link.MidUserPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;




@Repository
public interface MidUserPostEntityRepository  extends JpaRepository<MidUserPostEntity, Long>, JpaSpecificationExecutor<MidUserPostEntity> {

}
