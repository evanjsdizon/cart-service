package com.mycompany.cart.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.cart.BaseTest;
import com.mycompany.cart.dto.CartDto;
import com.mycompany.cart.dto.Result;
import com.mycompany.cart.dto.ResultCode;
import com.mycompany.cart.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CartController.class)
class CartControllerTest extends BaseTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper mapper;

  @MockBean private CartService cartService;

  @Test
  void givenValidResult_whenGetCart_thenReturnSuccess() throws Exception {
    when(cartService.getCart(anyLong())).thenReturn(getCartDtoResult());
    var expected = mapper.writeValueAsString(getCartDtoResult());

    mockMvc
        .perform(get(CART).param(USER_ID, "0"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(expected));
  }

  @Test
  void givenErrorResult_whenGetCart_thenReturnError() throws Exception {
    var errorResult = new Result<CartDto>(ResultCode.ERROR, String.format(NO_USER_FOUND, 0L));
    when(cartService.getCart(anyLong())).thenReturn(errorResult);
    var expected = mapper.writeValueAsString(errorResult);

    mockMvc
        .perform(get(CART).param(USER_ID, "0"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(expected));
  }

  @Test
  void givenException_whenGetCart_thenReturnError() throws Exception {
    when(cartService.getCart(anyLong())).thenThrow(RuntimeException.class);
    var expected =
        mapper.writeValueAsString(
            new Result<CartDto>(ResultCode.ERROR, String.format(FAILED_GET_CART, 0L)));

    mockMvc
        .perform(get(CART).param(USER_ID, "0"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(expected));
  }

  @Test
  void givenMissingParam_whenGetCart_thenReturnError() throws Exception {
    var expected =
        mapper.writeValueAsString(
            new Result<>(ResultCode.ERROR, String.format(MISSING_PARAM, USER_ID)));

    mockMvc
        .perform(get(CART))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(expected));
  }

  @Test
  void givenInvalidParamValue_whenGetCart_thenReturnError() throws Exception {
    String invalidValue = "@";
    var expected =
        mapper.writeValueAsString(
            new Result<>(
                ResultCode.ERROR, String.format(INVALID_PARAM_VALUE, USER_ID, invalidValue)));

    mockMvc
        .perform(get(CART).param(USER_ID, invalidValue))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(expected));
  }

  @Test
  void givenValidResult_whenAddToCart_thenReturnSuccess() throws Exception {
    var cartDtoResult = getCartDtoResult();
    cartDtoResult.getData().getProducts().add(getProductDto(3L, PRODUCT_THREE, PRICE_THREE));
    cartDtoResult.getData().setTotalPrice(PRICE_THREE.add(cartDtoResult.getData().getTotalPrice()));

    when(cartService.addToCart(anyLong(), anyLong())).thenReturn(cartDtoResult);
    var expected = mapper.writeValueAsString(cartDtoResult);

    mockMvc
        .perform(post(CART).param(USER_ID, "0").param(PRODUCT_ID, "0"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(expected));
  }

  @Test
  void givenException_whenAddToCart_thenReturnError() throws Exception {
    when(cartService.addToCart(anyLong(), anyLong())).thenThrow(RuntimeException.class);
    var expected =
        mapper.writeValueAsString(
            new Result<CartDto>(ResultCode.ERROR, String.format(FAILED_ADD_TO_CART, 0L, 0L)));

    mockMvc
        .perform(post(CART).param(USER_ID, "0").param(PRODUCT_ID, "0"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(expected));
  }

  @Test
  void givenValidResult_whenDeleteFromCart_thenReturnSuccess() throws Exception {
    when(cartService.deleteCartProduct(anyLong(), anyLong())).thenReturn(getCartDtoResult());
    var expected = mapper.writeValueAsString(getCartDtoResult());

    mockMvc
        .perform(delete(CART).param(USER_ID, "0").param(PRODUCT_ID, "0"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(expected));
  }

  @Test
  void givenException_whenDeleteFromCart_thenReturnError() throws Exception {
    when(cartService.deleteCartProduct(anyLong(), anyLong())).thenThrow(RuntimeException.class);
    var expected =
        mapper.writeValueAsString(
            new Result<CartDto>(ResultCode.ERROR, String.format(FAILED_DELETE_FROM_CART, 0L, 0L)));

    mockMvc
        .perform(delete(CART).param(USER_ID, "0").param(PRODUCT_ID, "0"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(expected));
  }
}
