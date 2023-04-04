package com.happytech.electronic.store.service.impl;

import com.happytech.electronic.store.config.AppConstants;
import com.happytech.electronic.store.dtos.PageableResponse;
import com.happytech.electronic.store.dtos.ProductDto;
import com.happytech.electronic.store.exception.ResourceNotFoundException;
import com.happytech.electronic.store.helper.Sort_Helper;
import com.happytech.electronic.store.model.Product;
import com.happytech.electronic.store.repository.ProductRepository;
import com.happytech.electronic.store.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
  private  ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Initiating request for create product to dao call {} ",productDto);

        Product product = this.modelMapper.map(productDto, Product.class);

        product.setIsactive(AppConstants.YES);

        Product saveProduct = productRepository.save(product);


        ProductDto savedProduct = this.modelMapper.map(saveProduct, ProductDto.class);

        log.info("completed request for saved user from dao call {} ",savedProduct);


        return savedProduct;
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto productDto) {

        log.info("Initiating request for update product to dao call {} ",productId);
        log.info("Initiating request for update product to dao call {} ",productDto);

        // Product product = this.modelMapper.map(productDto, Product.class);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT, AppConstants.PRODUCT_ID, productId));
        product.setTitle(productDto.getTitle());
        product.setBrand(productDto.getBrand());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setRating(productDto.getRating());
        product.setSku(productDto.getSku());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantityInStock(productDto.getQuantityInStock());
        product.setStock(productDto.isStock());
        product.setLive(productDto.isLive());

        Product updateProduct = productRepository.save(product);



        ProductDto updatedProduct = this.modelMapper.map(updateProduct, ProductDto.class);
        log.info("completed request for updated product from dao call {} ",updatedProduct);

        return updatedProduct;
    }

    @Override
    public void deleteProduct(Long productId) {

        log.info("Initiating request for delete product to dao call {} ",productId);

        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT, AppConstants.PRODUCT_ID, productId));
         product.setIsactive(AppConstants.NO);


        productRepository.save(product);
        log.info("completed request for deleted product from dao call {} ",product);


    }

    @Override
    public ProductDto getProductById(Long productId) {

        log.info("Initiating request for get ProductId to dao call {} ",productId);

        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT, AppConstants.PRODUCT_ID, productId));

        boolean equals = product.getIsactive().equals(AppConstants.NO);
        ProductDto getSingleProduct = this.modelMapper.map(product, ProductDto.class);



        log.info("completed request for get ProductId from dao call {} ",getSingleProduct);

        return getSingleProduct;
    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        log.info("Initiating request for getAllProducts to dao call ");

        Sort sort = (sortDir.equalsIgnoreCase(AppConstants.SORT_DIR_DESC))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Product> page = this.productRepository.findAll(pageable);

//         page.stream().filter(p -> p.getIsactive().equals(AppConstants.YES)).collect(Collectors.toList());
        PageableResponse<ProductDto> pagebleResponse = Sort_Helper.getPagebleResponse(page, ProductDto.class);

        log.info("completed request getAllProducts from dao call {} ",pagebleResponse);

        return pagebleResponse;
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        log.info("Initiating request for getAllLive to dao call ");

        Sort sort = (sortDir.equalsIgnoreCase(AppConstants.SORT_DIR))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Product> page = productRepository.findByLiveTrue(pageable);

        page.stream().filter(p -> p.getIsactive().equals(AppConstants.YES)).collect(Collectors.toList());
        PageableResponse<ProductDto> pagebleResponse = Sort_Helper.getPagebleResponse(page, ProductDto.class);
        log.info("completed request for  getAllLive from dao call {} ",pagebleResponse);

        return pagebleResponse;

    }

    @Override
    public PageableResponse<ProductDto> searchProductsByKeyword(String subTitle,Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Initiating request for searchProductsByKeyword to dao call {} ",subTitle);


        Sort sort = (sortDir.equalsIgnoreCase(AppConstants.SORT_DIR))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Product> byTitleContaining = productRepository.findByTitleContaining(subTitle,pageable);
        byTitleContaining.stream().filter(p -> p.getIsactive().equals(AppConstants.YES)).collect(Collectors.toList());
        PageableResponse<ProductDto> pagebleResponse = Sort_Helper.getPagebleResponse(byTitleContaining, ProductDto.class);

        log.info("completed request for searchProductsByKeyword  from dao call {} ",pagebleResponse);

        return pagebleResponse;
    }

    @Override
    public PageableResponse<ProductDto> getProductsByBrand(String brand, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Initiating request for getproductByBrand to dao call {} ",brand);


        Sort sort = (sortDir.equalsIgnoreCase(AppConstants.SORT_DIR))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Product> page = productRepository.findProductsByBrand(brand,pageable);

        page.stream().filter(p -> p.getIsactive().equals(AppConstants.YES)).collect(Collectors.toList());
        PageableResponse<ProductDto> pagebleResponse = Sort_Helper.getPagebleResponse(page, ProductDto.class);

        log.info("completed request for getproductByBrand from dao call {} ",pagebleResponse);

        return pagebleResponse;
    }
}
