package com.github.huifer.user.link.repository;


import com.github.huifer.user.link.entity.MidUserDeptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;




@Repository
public interface MidUserDeptEntityRepository  extends JpaRepository<MidUserDeptEntity, Long>, JpaSpecificationExecutor<MidUserDeptEntity> {

}
