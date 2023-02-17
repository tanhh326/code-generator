package com.youcon.bp.cg.go;


import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@io.swagger.v3.oas.annotations.media.Schema(description = "岗位修改参数")
public class PostEntityUpdateRequest {


    /**
     * 岗位名称
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="岗位名称")
    private String  name;




    @io.swagger.v3.oas.annotations.media.Schema(description="主键")
    private Long id;



}