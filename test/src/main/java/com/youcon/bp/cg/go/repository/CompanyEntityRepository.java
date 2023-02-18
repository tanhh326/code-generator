package com.youcon.bp.cg.go.repository;


import com.youcon.bp.cg.go.engity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


public interface CompanyEntityRepository  extends JpaRepository<CompanyEntity, Long>, JpaSpecificationExecutor<CompanyEntity> {

}
