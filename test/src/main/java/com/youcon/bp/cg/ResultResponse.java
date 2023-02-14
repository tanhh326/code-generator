package com.youcon.bp.cg;

import lombok.Data;

@Data
public class ResultResponse<T> {

  public static final String SUCCESS_CODE = "ok";
  public static final String ERROR_CODE = "error";
  private String code;
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

  public static <T> ResultResponse<T> error(String code, String msg) {

    ResultResponse resultResponse = new ResultResponse();
    resultResponse.setCode(code);
    resultResponse.setData(null);
    resultResponse.setMsg(msg);

    return resultResponse;
  }

}
