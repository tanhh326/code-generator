package com.github.huifer.user.servlet;

import java.util.List;

import java.math.BigDecimal;

import java.time.LocalDateTime;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@io.swagger.v3.oas.annotations.media.Schema(description = "用户查询参数")
public class UserEntityQueryRequest {


    /**
     * 用户名
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="用户名")
    private String  username;



    /**
     * 年龄
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="年龄")
    private BigDecimal  age;

    private List<BigDecimal> ages;


    /**
     * 密码
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="密码")
    private String  password;



    /**
     * 邮箱
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="邮箱")
    private String  email;



    /**
     * 手机
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="手机")
    private String  phone;



    /**
     * 生日
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="生日")
    private LocalDateTime  day;








}