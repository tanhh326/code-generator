package com.youcon.bp.cg.go;

import java.util.List;

import java.math.BigDecimal;


import javax.persistence.*;
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







}