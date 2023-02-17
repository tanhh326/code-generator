
package com.youcon.bp.cg.go.module.db;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;

@Entity
@Table( name = "company")
@Getter
@Setter
@com.baomidou.mybatisplus.annotation.TableName("company")
public class CompanyEntity {


    /**
     * 单位名称
     **/
    @Column(name = "name")
    @com.baomidou.mybatisplus.annotation.TableField(value = "name")
    private String  name;
    /**
     * 父id
     **/
    @Column(name = "pid")
    @com.baomidou.mybatisplus.annotation.TableField(value = "pid")
    private Long  pid;
    /**
     * 图标
     **/
    @Column(name = "logo")
    @com.baomidou.mybatisplus.annotation.TableField(value = "logo")
    private String  logo;
    /**
     * 地址
     **/
    @Column(name = "address")
    @com.baomidou.mybatisplus.annotation.TableField(value = "address")
    private String  address;



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