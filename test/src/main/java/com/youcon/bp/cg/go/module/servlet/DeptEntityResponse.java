package com.youcon.bp.cg.go.module.servlet;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;
@io.swagger.v3.oas.annotations.media.Schema(description = "部门响应参数")
@Getter
@Setter
public class DeptEntityResponse {


    /**
     * 部门名称
     **/
    @Column(name = "name")
    @com.baomidou.mybatisplus.annotation.TableField(value = "name")
    @io.swagger.v3.oas.annotations.media.Schema(description="部门名称")
    private String  name;
    /**
     * 单位id
     **/
    @Column(name = "companyId")
    @com.baomidou.mybatisplus.annotation.TableField(value = "companyId")
    @io.swagger.v3.oas.annotations.media.Schema(description="单位id")
    private Long  companyId;
    /**
     * 父id
     **/
    @Column(name = "pid")
    @com.baomidou.mybatisplus.annotation.TableField(value = "pid")
    @io.swagger.v3.oas.annotations.media.Schema(description="父id")
    private Long  pid;
    /**
     * 领导人
     **/
    @Column(name = "leader")
    @com.baomidou.mybatisplus.annotation.TableField(value = "leader")
    @io.swagger.v3.oas.annotations.media.Schema(description="领导人")
    private Long  leader;



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