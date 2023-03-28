package com.happytech.electronic.store.service;

import com.happytech.electronic.store.dtos.CategoryDto;
import com.happytech.electronic.store.dtos.PageableResponse;

import java.util.List;

public interface CategoryService {

    //create

    CategoryDto createCategory(CategoryDto categoryDto);

    //update

    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);

    //delete
    void deleteCategory(Long categoryId);

    //getAll

    PageableResponse<CategoryDto> getAllCategory(Integer pageNumber,Integer pageSize,String sortBy,String sortDir );

    //get single category details

    CategoryDto getSingleCategory(Long categoryId);

    //search
 List<CategoryDto> searchCategory(String keyword);


}
