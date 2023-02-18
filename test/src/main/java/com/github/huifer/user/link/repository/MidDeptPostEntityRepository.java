package com.github.huifer.user.link.repository;


import com.github.huifer.user.link.entity.MidDeptPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;




@Repository
public interface MidDeptPostEntityRepository  extends JpaRepository<MidDeptPostEntity, Long>, JpaSpecificationExecutor<MidDeptPostEntity> {

}
