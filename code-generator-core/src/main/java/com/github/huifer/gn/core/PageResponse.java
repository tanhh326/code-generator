package com.github.huifer.gn.core;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

  private long total;
  private int page;
  private int size;
  private List<T> data;


}
