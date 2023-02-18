package com.youcon.bp.cg;

import lombok.Data;

@Data
public class ResultResponse<T> {

  public static final Integer SUCCESS_CODE = 20000;
  public static final Integer ERROR_CODE = 40501;
  private Integer code;
  private T data;
  private String msg;

  public static <T> ResultResponse<T> ok(String msg, T data) {

    ResultResponse resultResponse = new ResultResponse();
    resultResponse.setCode(SUCCESS_CODE);
    resultResponse.setData(data);
    resultResponse.setMsg(msg);

    return resultResponse;
  }

  public static <T> ResultResponse<T> ok(T data) {

    ResultResponse resultResponse = new ResultResponse();
    resultResponse.setCode(SUCCESS_CODE);
    resultResponse.setData(data);
    resultResponse.setMsg("操作成功");

    return resultResponse;
  }

  public static <T> ResultResponse<T> ok(String msg) {

    ResultResponse resultResponse = new ResultResponse();
    resultResponse.setCode(SUCCESS_CODE);
    resultResponse.setData(null);
    resultResponse.setMsg(msg);

    return resultResponse;
  }


  public static <T> ResultResponse<T> error(String msg) {

    ResultResponse resultResponse = new ResultResponse();
    resultResponse.setCode(ERROR_CODE);
    resultResponse.setData(null);
    resultResponse.setMsg(msg);

    return resultResponse;
  }

  public static <T> ResultResponse<T> error(Integer code, String msg) {

    ResultResponse resultResponse = new ResultResponse();
    resultResponse.setCode(code);
    resultResponse.setData(null);
    resultResponse.setMsg(msg);

    return resultResponse;
  }

}
