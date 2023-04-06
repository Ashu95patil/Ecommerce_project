package com.happytech.electronic.store.controller;

import com.happytech.electronic.store.config.AppConstants;
import com.happytech.electronic.store.dtos.ApiResponse;
import com.happytech.electronic.store.dtos.PageableResponse;
import com.happytech.electronic.store.dtos.ProductDto;
import com.happytech.electronic.store.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        log.info("Entering into the method : this is the productDto {} ",productDto);


        ProductDto saveProduct = productService.createProduct(productDto);
        log.info("Exiting into the method : this is the saveProduct of productDto  {} ",saveProduct);

        return new ResponseEntity<>(saveProduct, HttpStatus.CREATED);

    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto, @PathVariable Long productId) {
        log.info("Entering into the method : this is the productDto {} ",productDto);
        log.info("Entering into the method : this is the productId {} ",productId);


        ProductDto updateProduct = productService.updateProduct(productId, productDto);
        log.info("Exiting into the method : this is the updateProduct of prodctDto  {} ",updateProduct);

        return new ResponseEntity<>(updateProduct, HttpStatus.CREATED);

    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
        log.info("Entering into the method : this is the productId {} ",productId);

        productService.deleteProduct(productId);

        ApiResponse apiResponse = ApiResponse.builder().message(AppConstants.PRODUCT_DELETE).success(true).status(HttpStatus.OK).build();

        log.info("Exiting into the method : this is the apiResponse of productDto  {} ",apiResponse);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getSinglProduct(@PathVariable Long productId) {
        log.info("Entering into the method : this is the productId {} ",productId);


        ProductDto productById = productService.getProductById(productId);

        log.info("Exiting into the method : this is the productById of productDto  {} ",productById);

        return new ResponseEntity<>(productById, HttpStatus.OK);

    }

    @GetMapping("/all")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProduct(@RequestParam(value = AppConstants.NO_VALUE, defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                      @RequestParam(value = AppConstants.SIZE_VALUE, defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                      @RequestParam(value = AppConstants.BY_VALUE, defaultValue = AppConstants.SORT_BYProd, required = false) String sortBy,
                                                                      @RequestParam(value = AppConstants.DIR_VALUE, defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        log.info("Entering into the method : this is the allProduct ");


        PageableResponse<ProductDto> allProducts = productService.getAllProducts(pageNumber, pageSize, sortBy, sortDir);
        log.info("Exiting into the method : this is the allProducts of productDto  {} ",allProducts);


        return new ResponseEntity<>(allProducts, HttpStatus.OK);

    }

    @GetMapping("/live/")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(@RequestParam(value = AppConstants.NO_VALUE, defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                   @RequestParam(value = AppConstants.SIZE_VALUE, defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                   @RequestParam(value = AppConstants.BY_VALUE, defaultValue = AppConstants.SORT_BYProd, required = false) String sortBy,
                                                                   @RequestParam(value = AppConstants.DIR_VALUE, defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {


        log.info("Entering into the method : this is the liveProduct ");

        PageableResponse<ProductDto> allLive = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        log.info("Exiting into the method : this is the allLive of productDto  {} ",allLive);

        return new ResponseEntity<>(allLive, HttpStatus.OK);

    }

    @GetMapping("/keywords/{subTitle}/")
    public ResponseEntity<PageableResponse<ProductDto>> searchProductByKeywords(@PathVariable String subTitle, @RequestParam(value = AppConstants.NO_VALUE, defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                                @RequestParam(value = AppConstants.SIZE_VALUE, defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                                @RequestParam(value = AppConstants.BY_VALUE, defaultValue = AppConstants.SORT_BYProd, required = false) String sortBy,
                                                                                @RequestParam(value = AppConstants.DIR_VALUE, defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        log.info("Entering into the method : this is the subTitle {} ",subTitle);


        PageableResponse<ProductDto> allKeywords = productService.searchProductsByKeyword(subTitle, pageNumber, pageSize, sortBy, sortDir);

        log.info("Exiting into the method : this is the allKeywords of productDto  {} ",allKeywords);

        return new ResponseEntity<>(allKeywords, HttpStatus.OK);

    }

    @GetMapping("/brands/{brand}/")
    public ResponseEntity<PageableResponse<ProductDto>> getProductsByBrand(@PathVariable String brand, @RequestParam(value = AppConstants.NO_VALUE, defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                           @RequestParam(value = AppConstants.SIZE_VALUE, defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                           @RequestParam(value = AppConstants.BY_VALUE, defaultValue = AppConstants.SORT_BYProd, required = false) String sortBy,
                                                                           @RequestParam(value = AppConstants.DIR_VALUE, defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        log.info("Entering into the method : this is the productBrand {} ",brand);

        PageableResponse<ProductDto> allBrands = productService.getProductsByBrand(brand, pageNumber, pageSize, sortBy, sortDir);

        log.info("Exiting into the method : this is the allBrands of productDto  {} ",allBrands);

        return new ResponseEntity<>(allBrands, HttpStatus.OK);

    }


}
