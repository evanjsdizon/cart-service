package com.mycompany.cart.service;

import com.mycompany.cart.dto.ProductDto;
import com.mycompany.cart.dto.Result;
import java.util.List;

public interface ProductService {

  Result<List<ProductDto>> getAllProducts();
}
