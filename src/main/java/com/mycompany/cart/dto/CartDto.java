package com.mycompany.cart.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class CartDto {

  private Long id;

  private Long userId;

  private BigDecimal totalPrice;

  private List<ProductDto> products;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CartDto cartDto = (CartDto) o;
    return id != null && cartDto.id != null && Objects.equals(id, cartDto.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  public List<ProductDto> getProducts() {
    return products;
  }

  public void setProducts(List<ProductDto> products) {
    this.products = products;
  }
}
