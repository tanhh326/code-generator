
package com.github.huifer.user.link.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table( name = "mid_dept_post")
@Getter
@Setter
@com.baomidou.mybatisplus.annotation.TableName("mid_dept_post")
public class MidDeptPostEntity {


    /**
     * 
     **/
    @Column(name = "dept_id")
    @com.baomidou.mybatisplus.annotation.TableField(value = "dept_id")
    private Long  deptId;
    /**
     * 
     **/
    @Column(name = "post_id")
    @com.baomidou.mybatisplus.annotation.TableField(value = "post_id")
    private Long  postId;



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