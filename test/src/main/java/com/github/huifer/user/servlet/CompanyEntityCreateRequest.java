package com.github.huifer.user.servlet;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@io.swagger.v3.oas.annotations.media.Schema(description = "单位创建参数")
public class CompanyEntityCreateRequest {


    /**
     * 单位名称
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="单位名称")
    private String  name;
    /**
     * 父id
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="父id")
    private Long  pid;
    /**
     * 图标
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="图标")
    private String  logo;
    /**
     * 地址
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="地址")
    private String  address;





}