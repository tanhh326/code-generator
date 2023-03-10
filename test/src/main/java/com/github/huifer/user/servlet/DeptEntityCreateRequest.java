package com.github.huifer.user.servlet;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@io.swagger.v3.oas.annotations.media.Schema(description = "部门创建参数")
public class DeptEntityCreateRequest {


    /**
     * 部门名称
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="部门名称")
    private String  name;
    /**
     * 单位id
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="单位id")
    private Long  companyId;
    /**
     * 父id
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="父id")
    private Long  pid;
    /**
     * 领导人
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="领导人")
    private Long  leader;





}