package com.mycompany.cart.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.mycompany.cart.BaseTest;
import com.mycompany.cart.dto.CartDto;
import com.mycompany.cart.dto.ProductDto;
import com.mycompany.cart.dto.ResultCode;
import com.mycompany.cart.model.Product;
import com.mycompany.cart.repository.CartProductRepo;
import com.mycompany.cart.repository.CartRepo;
import com.mycompany.cart.repository.ProductRepo;
import com.mycompany.cart.repository.UserRepo;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.CollectionUtils;

@SpringBootTest
@ActiveProfiles("test")
class CartServiceTest extends BaseTest {

  @Autowired private CartService cartService;

  @MockBean private CartRepo cartRepo;

  @MockBean private UserRepo userRepo;

  @MockBean private ProductRepo productRepo;

  @MockBean private CartProductRepo cartProductRepo;

  @Test
  void givenValidResult_whenGetCart_thenReturnSuccess() {
    var expected = getCartDto();
    when(cartRepo.findByUserId(anyLong())).thenReturn(getValidCart());
    when(userRepo.findById(anyLong())).thenReturn(Optional.of(getValidUser()));
    var actual = cartService.getCart(0L).getData();

    assertEquals(expected, actual);
    assertEquals(expected.getProducts(), actual.getProducts());
  }

  @Test
  void givenEmptyResult_whenGetCart_thenReturnSuccess() {
    when(cartRepo.findByUserId(anyLong())).thenReturn(null);
    when(userRepo.findById(anyLong())).thenReturn(Optional.of(getValidUser()));
    var actual = cartService.getCart(0L).getData();

    assertNull(actual.getId());
    assertTrue(CollectionUtils.isEmpty(actual.getProducts()));
  }

  @Test
  void givenInvalidUser_whenGetCart_thenReturnNotFound() {
    var actual = cartService.getCart(0L);
    assertEquals(ResultCode.NOT_FOUND, actual.getCode());
  }

  @Test
  void givenExistingCart_whenAddToCart_thenReturnSuccess() {
    var expected = getCartDto();
    expected.getProducts().add(getProductDto(3L, PRODUCT_THREE, PRICE_THREE));
    expected.setTotalPrice(PRICE_THREE.add(expected.getTotalPrice()));

    var validCart = getValidCart();
    var validProduct = getValidProduct(3L, PRODUCT_THREE, PRICE_THREE, 1);
    var validCartProduct = getValidCartProduct(3L, validCart, validProduct);
    validCart.getProducts().add(validCartProduct);

    when(userRepo.findById(anyLong())).thenReturn(Optional.of(getValidUser()));
    when(productRepo.findById(anyLong())).thenReturn(Optional.of(validProduct));
    when(cartRepo.findByUserId(anyLong())).thenReturn(validCart);
    var actual = cartService.addToCart(0L, 0L).getData();

    assertEquals(expected, actual);
    assertEquals(expected.getProducts().get(2), actual.getProducts().get(2));
    assertEquals(expected.getTotalPrice(), actual.getTotalPrice());
  }

  @Test
  void givenNullCart_whenAddToCart_thenReturnSuccess() {
    var expected = new CartDto();
    expected.setProducts(List.of(getProductDto(3L, PRODUCT_THREE, PRICE_THREE)));
    expected.setTotalPrice(PRICE_THREE);

    when(userRepo.findById(anyLong())).thenReturn(Optional.of(getValidUser()));
    when(productRepo.findById(anyLong()))
        .thenReturn(Optional.of(getValidProduct(3L, PRODUCT_THREE, PRICE_THREE, 1)));
    var actual = cartService.addToCart(0L, 0L).getData();

    assertEquals(expected.getProducts().get(0), actual.getProducts().get(0));
    assertEquals(expected.getTotalPrice(), actual.getTotalPrice());
  }

  @Test
  void givenInvalidUser_whenAddToCart_thenReturnNotFound() {
    var actual = cartService.addToCart(0L, 0L);
    assertEquals(ResultCode.NOT_FOUND, actual.getCode());
  }

  @Test
  void givenNullResult_whenAddToCart_thenReturnNotFound() {
    when(userRepo.findById(anyLong())).thenReturn(Optional.of(getValidUser()));
    var actual = cartService.addToCart(0L, 0L);
    assertEquals(ResultCode.NOT_FOUND, actual.getCode());
  }

  @Test
  void givenOutOfStock_whenAddToCart_thenReturnError() {
    var outOfStockProduct = new Product();
    outOfStockProduct.setCountRemaining(-99999);

    when(userRepo.findById(anyLong())).thenReturn(Optional.of(getValidUser()));
    when(productRepo.findById(anyLong())).thenReturn(Optional.of(outOfStockProduct));
    var actual = cartService.addToCart(0L, 0L);

    assertEquals(ResultCode.ERROR, actual.getCode());
  }

  @Test
  void givenValidResult_whenDeleteCartProduct_thenReturnSuccess() {
    var validCart = getValidCart();
    var validCartProduct = validCart.getProducts().get(1);
    var expectedTotalPrice =
        validCart.getTotalPrice().subtract(validCartProduct.getProduct().getPrice());

    when(userRepo.findById(anyLong())).thenReturn(Optional.of(getValidUser()));
    when(cartRepo.findByUserId(anyLong())).thenReturn(validCart);
    when(cartProductRepo.findAllByProductIdAndCartUserId(anyLong(), anyLong()))
        .thenReturn(List.of(validCartProduct));

    var actual = cartService.deleteCartProduct(0L, 0L).getData();
    var productDto = new ProductDto();
    productDto.setId(2L);

    assertEquals(expectedTotalPrice, actual.getTotalPrice());
    assertFalse(actual.getProducts().contains(productDto));
  }

  @Test
  void givenInvalidUser_whenDeleteCardProduct_thenReturnNotFound() {
    var actual = cartService.deleteCartProduct(0L, 0L);
    assertEquals(ResultCode.NOT_FOUND, actual.getCode());
  }
}
