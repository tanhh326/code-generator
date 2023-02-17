package com.youcon.bp.cg.go.module.servlet;


import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@io.swagger.v3.oas.annotations.media.Schema(description = "岗位创建参数")
public class PostEntityCreateRequest {


    /**
     * 岗位名称
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="岗位名称")
    private String  name;





}