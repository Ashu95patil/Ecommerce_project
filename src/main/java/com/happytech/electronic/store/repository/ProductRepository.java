package com.happytech.electronic.store.repository;

import com.happytech.electronic.store.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    Page<Product> findProductsByBrand(String brand, Pageable pageable);

    Page<Product> findByLiveTrue(Pageable pageable);

    Page<Product> findByTitleContaining(String subTitle, Pageable pageable);

}
