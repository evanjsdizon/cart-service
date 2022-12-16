package com.mycompany.cart.controller;

import com.mycompany.cart.dto.CartDto;
import com.mycompany.cart.dto.Result;
import com.mycompany.cart.dto.ResultCode;
import com.mycompany.cart.service.CartService;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController extends BaseController {

  private static final Logger log = LoggerFactory.getLogger(CartController.class);

  @Autowired private CartService cartService;

  @GetMapping
  public Result<CartDto> getCart(@RequestParam("user-id") @NotNull Long userId) {
    try {
      return cartService.getCart(userId);
    } catch (Exception e) {
      var msg = String.format("Failed to get shopping cart with userId=[%s]", userId);
      log.error(msg, e);
      return new Result<>(ResultCode.ERROR, msg);
    }
  }

  @PostMapping
  public Result<CartDto> addToCart(
      @RequestParam("user-id") @NotNull Long userId,
      @RequestParam("product-id") @NotNull Long productId) {
    try {
      return cartService.addToCart(userId, productId);
    } catch (Exception e) {
      var msg =
          String.format(
              "Failed to add product to shopping cart with userId=[%s] and productId=[%s]",
              userId, productId);
      log.error(msg, e);
      return new Result<>(ResultCode.ERROR, msg);
    }
  }

  @DeleteMapping
  public Result<CartDto> deleteFromCart(
      @RequestParam("user-id") @NotNull Long userId,
      @RequestParam("product-id") @NotNull Long productId) {
    try {
      return cartService.deleteCartProduct(userId, productId);
    } catch (Exception e) {
      var msg =
          String.format(
              "Failed to delete product from shopping cart with userId=[%s] and productId=[%s]",
              userId, productId);
      log.error(msg, e);
      return new Result<>(ResultCode.ERROR, msg);
    }
  }
}
