package com.mycompany.cart.controller;

import com.mycompany.cart.dto.Result;
import com.mycompany.cart.dto.ResultCode;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public class BaseController {

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public <T> Result<T> handleExceptions(MissingServletRequestParameterException e) {
    return new Result<>(ResultCode.ERROR, e.getMessage());
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public <T> Result<T> handleExceptions(MethodArgumentTypeMismatchException e) {
    var msg = String.format("Parameter '%s' has an error: %s", e.getName(), e.getMessage());
    return new Result<>(ResultCode.ERROR, msg);
  }
}
