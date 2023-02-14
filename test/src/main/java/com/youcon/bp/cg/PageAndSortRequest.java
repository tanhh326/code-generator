package com.youcon.bp.cg;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PageAndSortRequest {


  @Schema(title = "排序对象", description = "排序对象")
  private SortRequest sort;
  @Schema(title = "页码", description = "页码")
  private int page = 0;
  @Schema(title = "页量", description = "页量")
  private int size = 10;


}
