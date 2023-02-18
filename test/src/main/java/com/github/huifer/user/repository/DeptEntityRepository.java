package com.github.huifer.user.repository;


import com.github.huifer.user.entity.DeptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;




@Repository
public interface DeptEntityRepository  extends JpaRepository<DeptEntity, Long>, JpaSpecificationExecutor<DeptEntity> {

}
