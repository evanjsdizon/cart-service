package com.mycompany.cart.repository;

import com.mycompany.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

  Cart findByUserId(Long userId);
}
