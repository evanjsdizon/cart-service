package com.mycompany.cart.dto;

public class Result<E> {

  private ResultCode code;

  private String message;

  private E data;

  public Result() {}

  public Result(ResultCode code, String message) {
    this.code = code;
    this.message = message;
  }

  public Result(ResultCode code, E data) {
    this.code = code;
    this.data = data;
  }

  public Result(ResultCode code, String message, E data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public ResultCode getCode() {
    return code;
  }

  public void setCode(ResultCode code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public E getData() {
    return data;
  }

  public void setData(E data) {
    this.data = data;
  }
}
