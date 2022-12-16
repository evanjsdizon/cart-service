package com.mycompany.cart.service;

import com.mycompany.cart.dto.ProductDto;
import com.mycompany.cart.dto.Result;
import com.mycompany.cart.dto.ResultCode;
import com.mycompany.cart.dto.transformer.ProductTransformer;
import com.mycompany.cart.repository.ProductRepo;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ProductServiceImpl implements ProductService {

  @Autowired private ProductRepo productRepo;

  @Override
  public Result<List<ProductDto>> getAllProducts() {
    var productDtoList =
        productRepo.findAll().stream()
            .map(ProductTransformer::toProductDto)
            .filter(Objects::nonNull)
            .toList();
    return new Result<>(ResultCode.OK, "Successfully fetched all products", productDtoList);
  }
}
