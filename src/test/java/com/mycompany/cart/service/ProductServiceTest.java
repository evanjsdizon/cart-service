package com.mycompany.cart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.mycompany.cart.BaseTest;
import com.mycompany.cart.repository.ProductRepo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest extends BaseTest {

  @Autowired private ProductService productService;

  @MockBean private ProductRepo productRepo;

  @Test
  void givenValidResult_whenGetAllProducts_thenReturnSuccess() {
    var expected = getProductDtos();
    when(productRepo.findAll()).thenReturn(getValidProducts());
    var actual = productService.getAllProducts().getData();

    assertEquals(expected, actual);
  }

  @Test
  void givenEmptyResult_whenGetAllProducts_thenReturnSuccess() {
    when(productRepo.findAll()).thenReturn(new ArrayList<>());
    var actual = productService.getAllProducts().getData();

    assertTrue(actual.isEmpty());
  }

  @Test
  void givenNullProducts_whenGetAllProducts_thenReturnSuccess() {
    var expected = Collections.singletonList(getProductDto(5L, "", BigDecimal.TEN));
    when(productRepo.findAll())
        .thenReturn(Arrays.asList(null, getValidProduct(5L, "", BigDecimal.TEN, 0)));
    var actual = productService.getAllProducts().getData();

    assertEquals(expected, actual);
  }
}
