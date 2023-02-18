package com.youcon.bp.cg.go.servlet;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;
@io.swagger.v3.oas.annotations.media.Schema(description = "用户响应参数")
@Getter
@Setter
public class UserEntityResponse {


    /**
     * 用户名
     **/
    @Column(name = "username")
    @com.baomidou.mybatisplus.annotation.TableField(value = "username")
    @io.swagger.v3.oas.annotations.media.Schema(description="用户名")
    private String  username;
    /**
     * 年龄
     **/
    @Column(name = "age")
    @com.baomidou.mybatisplus.annotation.TableField(value = "age")
    @io.swagger.v3.oas.annotations.media.Schema(description="年龄")
    private BigDecimal  age;
    /**
     * 密码
     **/
    @Column(name = "password")
    @com.baomidou.mybatisplus.annotation.TableField(value = "password")
    @io.swagger.v3.oas.annotations.media.Schema(description="密码")
    private String  password;
    /**
     * 邮箱
     **/
    @Column(name = "email")
    @com.baomidou.mybatisplus.annotation.TableField(value = "email")
    @io.swagger.v3.oas.annotations.media.Schema(description="邮箱")
    private String  email;



    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @com.baomidou.mybatisplus.annotation.TableId(type = com.baomidou.mybatisplus.annotation.IdType.AUTO)
    private Long id;
    @com.baomidou.mybatisplus.annotation.TableLogic
    @Column(name = "deleted")
    @com.baomidou.mybatisplus.annotation.TableField(value = "deleted")
    private Integer deleted;
    @Column(name = "customer")
    @com.baomidou.mybatisplus.annotation.TableField(value = "customer")
    private Boolean customer;
    @Column(name = "version")
    @com.baomidou.mybatisplus.annotation.TableField(value = "version")
    @Version
    @com.baomidou.mybatisplus.annotation.Version
    private Long version = 0L;
    @com.baomidou.mybatisplus.annotation.TableField(value = "create_time",fill = com.baomidou.mybatisplus.annotation.FieldFill.INSERT)
    @Column(name = "create_time")
    private LocalDateTime createTime;
    @com.baomidou.mybatisplus.annotation.TableField(value = "update_time",fill = com.baomidou.mybatisplus.annotation.FieldFill.UPDATE)
    @Column(name = "update_time")
    private LocalDateTime updateTime;
    @Column(name = "create_user_id")
    @com.baomidou.mybatisplus.annotation.TableField(value = "create_user_id",fill = com.baomidou.mybatisplus.annotation.FieldFill.INSERT)
    private String createUserId;
    @Column(name = "update_user_id")
    @com.baomidou.mybatisplus.annotation.TableField(value = "update_user_id",fill =com.baomidou.mybatisplus.annotation.FieldFill.UPDATE)
    private String updateUserId;

}