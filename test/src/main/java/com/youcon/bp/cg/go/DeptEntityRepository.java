package com.youcon.bp.cg.go;


import com.youcon.bp.cg.go.DeptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;




@Repository
public interface DeptEntityRepository  extends JpaRepository<DeptEntity, Long>, JpaSpecificationExecutor<DeptEntity> {

}
