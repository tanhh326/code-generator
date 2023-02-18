package com.youcon.bp.cg.go.repository;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youcon.bp.cg.go.engity.CompanyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


@Mapper
public interface CompanyEntityMapper extends BaseMapper<CompanyEntity> {

}
