package com.mycompany.cart.repository;

import com.mycompany.cart.model.CartProduct;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartProductRepo extends JpaRepository<CartProduct, Long> {

  List<CartProduct> findAllByProductIdAndCartUserId(Long id, Long userId);
}
