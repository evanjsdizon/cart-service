package com.mycompany.cart.dto.transformer;

import com.mycompany.cart.dto.CartDto;
import com.mycompany.cart.model.Cart;
import com.mycompany.cart.model.CartProduct;
import java.util.ArrayList;
import java.util.Objects;
import org.springframework.util.CollectionUtils;

public class CartTransformer {

  private CartTransformer() {}

  public static CartDto toCartDto(Cart cart) {
    var dto = new CartDto();
    dto.setProducts(new ArrayList<>());
    if (cart == null) return dto;

    dto.setId(cart.getId());
    dto.setUserId(cart.getUser().getId());
    dto.setTotalPrice(cart.getTotalPrice());
    if (!CollectionUtils.isEmpty(cart.getProducts())) {
      dto.setProducts(
          cart.getProducts().stream()
              .filter(Objects::nonNull)
              .map(CartProduct::getProduct)
              .map(ProductTransformer::toProductDto)
              .toList());
    }

    return dto;
  }
}
