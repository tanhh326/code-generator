package com.youcon.bp.cg.go;

import java.util.List;


import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@io.swagger.v3.oas.annotations.media.Schema(description = "单位查询参数")
public class CompanyEntityQueryRequest {


    /**
     * 单位名称
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="单位名称")
    private String  name;



    /**
     * 父id
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="父id")
    private Long  pid;



    /**
     * 图标
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="图标")
    private String  logo;



    /**
     * 地址
     **/
    @io.swagger.v3.oas.annotations.media.Schema(description="地址")
    private String  address;








}