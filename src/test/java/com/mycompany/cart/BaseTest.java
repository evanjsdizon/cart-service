package com.mycompany.cart;

import com.mycompany.cart.dto.CartDto;
import com.mycompany.cart.dto.ProductDto;
import com.mycompany.cart.dto.Result;
import com.mycompany.cart.dto.ResultCode;
import com.mycompany.cart.model.Cart;
import com.mycompany.cart.model.CartProduct;
import com.mycompany.cart.model.Product;
import com.mycompany.cart.model.User;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseTest {

  protected static final String PRODUCT_ONE = "Product One";
  protected static final String PRODUCT_TWO = "Product Two";
  protected static final String PRODUCT_THREE = "Product Three";
  protected static final BigDecimal PRICE_ONE = BigDecimal.valueOf(2500);
  protected static final BigDecimal PRICE_TWO = BigDecimal.valueOf(2499.99);
  protected static final BigDecimal PRICE_THREE = BigDecimal.valueOf(1799.50);
  protected static final BigDecimal PRICE_TOTAL = BigDecimal.valueOf(4999.99);
  protected static final String CART = "/cart";
  protected static final String PRODUCTS_ALL = "/product/all";
  protected static final String USER_ID = "user-id";
  protected static final String PRODUCT_ID = "product-id";
  protected static final String FAILED_GET_CART = "Failed to get shopping cart with userId=[%s]";
  protected static final String FAILED_ADD_TO_CART =
      "Failed to add product to shopping cart with userId=[%s] and productId=[%s]";
  protected static final String FAILED_DELETE_FROM_CART =
      "Failed to delete product from shopping cart with userId=[%s] and productId=[%s]";
  protected static final String NO_USER_FOUND = "No user found with userId=[%s].";
  protected static final String FAILED_GET_ALL_PRODUCTS = "Failed to fetch all products";
  protected static final String MISSING_PARAM =
      "Required request parameter '%s' for method parameter type Long is not present";
  protected static final String INVALID_PARAM_VALUE =
      "Parameter '%s' has an error: Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; For input string: \"%s\"";

  protected Result<CartDto> getCartDtoResult() {
    return new Result<>(ResultCode.OK, getCartDto());
  }

  protected CartDto getCartDto() {
    var cartDto = new CartDto();
    cartDto.setId(5L);
    cartDto.setUserId(10L);
    cartDto.setTotalPrice(PRICE_TOTAL);
    cartDto.setProducts(getProductDtos());

    return cartDto;
  }

  protected Result<List<ProductDto>> getProductDtoResult() {
    return new Result<>(ResultCode.OK, getProductDtos());
  }

  protected List<ProductDto> getProductDtos() {
    var productDtos = new ArrayList<ProductDto>();
    productDtos.add(getProductDto(1L, PRODUCT_ONE, PRICE_ONE));
    productDtos.add(getProductDto(2L, PRODUCT_TWO, PRICE_TWO));

    return productDtos;
  }

  protected ProductDto getProductDto(Long id, String name, BigDecimal price) {
    var productDto = new ProductDto();
    productDto.setId(id);
    productDto.setName(name);
    productDto.setPrice(price);

    return productDto;
  }

  protected Cart getValidCart() {
    var cart = new Cart();
    cart.setId(5L);
    cart.setUser(getValidUser());
    cart.setTotalPrice(PRICE_TOTAL);
    cart.setProducts(getValidCartProducts());

    return cart;
  }

  protected User getValidUser() {
    var user = new User();
    user.setId(10L);

    return user;
  }

  protected List<CartProduct> getValidCartProducts() {
    var cartProducts = new ArrayList<CartProduct>();
    cartProducts.add(getValidCartProduct(1L, 1L, PRODUCT_ONE, PRICE_ONE, 5));
    cartProducts.add(getValidCartProduct(2L, 2L, PRODUCT_TWO, PRICE_TWO, 3));

    return cartProducts;
  }

  protected CartProduct getValidCartProduct(
      Long cartProductId, Long productId, String name, BigDecimal price, Integer countRemaining) {
    var cartProduct = new CartProduct();
    cartProduct.setId(cartProductId);
    cartProduct.setProduct(getValidProduct(productId, name, price, countRemaining));

    return cartProduct;
  }

  protected CartProduct getValidCartProduct(Long cartProductId, Cart cart, Product product) {
    var cartProduct = new CartProduct();
    cartProduct.setId(cartProductId);
    cartProduct.setCart(cart);
    cartProduct.setProduct(product);

    return cartProduct;
  }

  protected List<Product> getValidProducts() {
    var products = new ArrayList<Product>();
    products.add(getValidProduct(1L, PRODUCT_ONE, PRICE_ONE, 5));
    products.add(getValidProduct(2L, PRODUCT_TWO, PRICE_TWO, 3));

    return products;
  }

  protected Product getValidProduct(
      Long id, String name, BigDecimal price, Integer countRemaining) {
    var product = new Product();
    product.setId(id);
    product.setName(name);
    product.setPrice(price);
    product.setCountRemaining(countRemaining);

    return product;
  }
}
