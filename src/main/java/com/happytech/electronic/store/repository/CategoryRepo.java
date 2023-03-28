package com.happytech.electronic.store.repository;

import com.happytech.electronic.store.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category,Long> {

    List<Category> findByTitleContaining(String Keywords);
}
