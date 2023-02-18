package com.github.huifer.user.repository;


import com.github.huifer.user.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;




@Repository
public interface PostEntityRepository  extends JpaRepository<PostEntity, Long>, JpaSpecificationExecutor<PostEntity> {

}
