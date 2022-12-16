package com.mycompany.cart.dto.transformer;

import com.mycompany.cart.dto.ProductDto;
import com.mycompany.cart.model.Product;

public class ProductTransformer {

  private ProductTransformer() {}

  public static ProductDto toProductDto(Product product) {
    if (product == null) return null;

    var dto = new ProductDto();
    dto.setId(product.getId());
    dto.setName(product.getName());
    dto.setPrice(product.getPrice());
    dto.setAvailable(product.getCountRemaining() != null ? product.getCountRemaining() > 0 : null);

    return dto;
  }
}
