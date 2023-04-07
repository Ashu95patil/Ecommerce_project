package com.happytech.electronic.store.service;

import com.happytech.electronic.store.dtos.PageableResponse;
import com.happytech.electronic.store.dtos.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(Long productId, ProductDto productDto);

    void deleteProduct(Long productId);

    ProductDto getProductById(Long productId);

    PageableResponse<ProductDto> getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    PageableResponse<ProductDto> getAllLive(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    PageableResponse<ProductDto> searchProductsByKeyword(String subTitle, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    PageableResponse<ProductDto> getProductsByBrand(String brand, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

}
