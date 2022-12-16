package com.mycompany.cart.controller;

import com.mycompany.cart.dto.ProductDto;
import com.mycompany.cart.dto.Result;
import com.mycompany.cart.dto.ResultCode;
import com.mycompany.cart.service.ProductService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController extends BaseController {

  private static final Logger log = LoggerFactory.getLogger(ProductController.class);

  @Autowired private ProductService productService;

  @GetMapping
  @RequestMapping("/all")
  public Result<List<ProductDto>> getAllProducts() {
    try {
      return productService.getAllProducts();
    } catch (Exception e) {
      var msg = "Failed to fetch all products";
      log.error(msg, e);
      return new Result<>(ResultCode.ERROR, msg);
    }
  }
}
