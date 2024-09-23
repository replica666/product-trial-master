package com.alten.shop.service;

import com.alten.shop.dto.request.ProductReqDto;
import com.alten.shop.dto.response.ProductResDto;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<ProductResDto> getAllProducts();
    ProductResDto getProductById(Long id);
    ProductResDto createProduct(ProductReqDto requestDto);
    ProductResDto updateProduct(Long id, Map<String, Object> fields) throws IllegalAccessException;
    void deleteProduct(Long id);
}
