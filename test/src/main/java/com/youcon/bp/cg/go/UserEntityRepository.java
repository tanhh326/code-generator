package com.youcon.bp.cg.go;


import com.youcon.bp.cg.go.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;




@Repository
public interface UserEntityRepository  extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

}
