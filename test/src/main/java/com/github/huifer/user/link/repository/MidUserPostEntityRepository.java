package com.github.huifer.user.link.repository;


import com.github.huifer.user.link.entity.MidUserPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;




@Repository
public interface MidUserPostEntityRepository  extends JpaRepository<MidUserPostEntity, Long>, JpaSpecificationExecutor<MidUserPostEntity> {

}
