package com.mycompany.cart.service;

import com.mycompany.cart.dto.CartDto;
import com.mycompany.cart.dto.Result;
import com.mycompany.cart.dto.ResultCode;
import com.mycompany.cart.dto.transformer.CartTransformer;
import com.mycompany.cart.model.Cart;
import com.mycompany.cart.model.CartProduct;
import com.mycompany.cart.model.Product;
import com.mycompany.cart.model.User;
import com.mycompany.cart.repository.CartProductRepo;
import com.mycompany.cart.repository.CartRepo;
import com.mycompany.cart.repository.ProductRepo;
import com.mycompany.cart.repository.UserRepo;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CartServiceImpl implements CartService {

  private static final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

  @Autowired private CartRepo cartRepo;

  @Autowired private UserRepo userRepo;

  @Autowired private ProductRepo productRepo;

  @Autowired private CartProductRepo cartProductRepo;

  @Override
  @Transactional(readOnly = true)
  public Result<CartDto> getCart(Long userId) {
    var userValidation = validateUser(userId);
    if (userValidation.getCode() != ResultCode.OK) {
      return new Result<>(userValidation.getCode(), userValidation.getMessage());
    }

    var cart = cartRepo.findByUserId(userId);

    return new Result<>(
        ResultCode.OK, "Successfully fetched shopping cart", CartTransformer.toCartDto(cart));
  }

  @Override
  public Result<CartDto> addToCart(Long userId, Long productId) {
    var userValidation = validateUser(userId);
    if (userValidation.getCode() != ResultCode.OK) {
      return new Result<>(userValidation.getCode(), userValidation.getMessage());
    }

    var user = userValidation.getData();
    var product = productRepo.findById(productId).orElse(null);
    if (product == null) {
      var msg = String.format("No product found with productId=[%s].", productId);
      log.debug(msg);

      return new Result<>(ResultCode.NOT_FOUND, msg);
    }
    if (product.getCountRemaining() <= 0) {
      var msg = String.format("Product no longer in stock with productId=[%s]", productId);
      log.debug(msg);

      return new Result<>(ResultCode.ERROR, msg);
    }

    var cart = cartRepo.findByUserId(userId);

    if (cart == null) {
      cart = new Cart();
      cart.setUser(user);
      var now = LocalDateTime.now();
      cart.setCreatedAt(now);
      cart.setUpdatedAt(now);
      cart.setTotalPrice(product.getPrice());
      cart.setProducts(List.of(getCartProduct(cart, product)));
    } else {
      cart.setUpdatedAt(LocalDateTime.now());
      cartProductRepo.save(getCartProduct(cart, product));
      cart.setTotalPrice(cart.getTotalPrice().add(product.getPrice()));
    }

    cartRepo.save(cart);
    return new Result<>(
        ResultCode.OK, "Successfully added product to cart", CartTransformer.toCartDto(cart));
  }

  @Override
  public Result<CartDto> deleteCartProduct(Long userId, Long productId) {
    var userValidation = validateUser(userId);
    if (userValidation.getCode() != ResultCode.OK) {
      return new Result<>(userValidation.getCode(), userValidation.getMessage());
    }

    var cart = cartRepo.findByUserId(userId);
    var cartProducts = cartProductRepo.findAllByProductIdAndCartUserId(productId, userId);
    if (!CollectionUtils.isEmpty(cartProducts)) {
      var cartProduct = cartProducts.get(0);
      cart.getProducts().remove(cartProduct);
      cart.setTotalPrice(cart.getTotalPrice().subtract(cartProduct.getProduct().getPrice()));
      cartProductRepo.delete(cartProduct);
    }

    return new Result<>(
        ResultCode.OK, "Successfully deleted product frorm cart", CartTransformer.toCartDto(cart));
  }

  private Result<User> validateUser(Long userId) {
    var user = userRepo.findById(userId);
    if (user.isEmpty()) {
      var msg = String.format("No user found with userId=[%s].", userId);
      log.debug(msg);

      return new Result<>(ResultCode.NOT_FOUND, msg);
    }

    return new Result<>(ResultCode.OK, user.get());
  }

  private CartProduct getCartProduct(Cart cart, Product product) {
    var cartProduct = new CartProduct();
    cartProduct.setCart(cart);
    cartProduct.setProduct(product);

    return cartProduct;
  }
}
