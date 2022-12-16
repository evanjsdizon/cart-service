package com.mycompany.cart.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.cart.BaseTest;
import com.mycompany.cart.dto.Result;
import com.mycompany.cart.dto.ResultCode;
import com.mycompany.cart.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerTest extends BaseTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper mapper;

  @MockBean private ProductService productService;

  @Test
  void givenValidResult_whenGetAllProducts_thenReturnSuccess() throws Exception {
    when(productService.getAllProducts()).thenReturn(getProductDtoResult());
    var expected = mapper.writeValueAsString(getProductDtoResult());

    mockMvc
        .perform(get(PRODUCTS_ALL))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(expected));
  }

  @Test
  void givenException_whenGetAllProducts_thenReturnError() throws Exception {
    when(productService.getAllProducts()).thenThrow(RuntimeException.class);
    var expected =
        mapper.writeValueAsString(new Result<>(ResultCode.ERROR, FAILED_GET_ALL_PRODUCTS));

    mockMvc
        .perform(get(PRODUCTS_ALL))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(expected));
  }
}
