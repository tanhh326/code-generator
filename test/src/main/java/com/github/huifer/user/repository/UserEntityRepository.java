package com.github.huifer;


import com.github.huifer.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;




@Repository
public interface UserEntityRepository  extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

}