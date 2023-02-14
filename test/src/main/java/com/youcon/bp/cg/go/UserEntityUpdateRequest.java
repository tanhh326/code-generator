package com.youcon.bp.cg.go;

import java.math.BigDecimal;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@io.swagger.v3.oas.annotations.media.Schema(description = "用户修改参数")
public class UserEntityUpdateRequest {


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




    @io.swagger.v3.oas.annotations.media.Schema(description="主键")
    private Long id;



}