package com.youcon.bp.cg.go;

import java.util.List;


import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@io.swagger.v3.oas.annotations.media.Schema(description = "岗位查询参数")
public class PostEntityQueryRequest {


    /**
     * 岗位名称
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="岗位名称")
    private String  name;








}