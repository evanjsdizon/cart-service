package com.mycompany.cart.service;

import com.mycompany.cart.dto.CartDto;
import com.mycompany.cart.dto.Result;

public interface CartService {

  Result<CartDto> getCart(Long userId);

  Result<CartDto> addToCart(Long userId, Long productId);

  Result<CartDto> deleteCartProduct(Long userId, Long productId);
}
