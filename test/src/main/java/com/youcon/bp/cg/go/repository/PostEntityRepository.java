package com.youcon.bp.cg.go.repository;


import com.youcon.bp.cg.go.engity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;




@Repository
public interface PostEntityRepository  extends JpaRepository<PostEntity, Long>, JpaSpecificationExecutor<PostEntity> {

}
