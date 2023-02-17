package com.youcon.bp.cg.go.repository;


import com.youcon.bp.cg.go.module.db.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;




@Repository
public interface CompanyEntityRepository  extends JpaRepository<CompanyEntity, Long>, JpaSpecificationExecutor<CompanyEntity> {

}
