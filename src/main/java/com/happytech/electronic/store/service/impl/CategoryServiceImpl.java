package com.happytech.electronic.store.service.impl;

import com.happytech.electronic.store.config.AppConstants;
import com.happytech.electronic.store.dtos.CategoryDto;
import com.happytech.electronic.store.dtos.PageableResponse;
import com.happytech.electronic.store.exception.CategoryNotFoundException;
import com.happytech.electronic.store.helper.Sort_Helper;
import com.happytech.electronic.store.model.Category;
import com.happytech.electronic.store.repository.CategoryRepo;
import com.happytech.electronic.store.service.CategoryService;
import lombok.experimental.Helper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category = this.modelMapper.map(categoryDto, Category.class);

        Category saveCat = categoryRepo.save(category);

        CategoryDto savecategoryDto = this.modelMapper.map(saveCat, CategoryDto.class);

        return savecategoryDto;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {

        //get category of given id
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));

        //update category
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());

        Category updatedCat = categoryRepo.save(category);

        return this.modelMapper.map(updatedCat,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long categoryId) {

        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));

        categoryRepo.delete(category);

    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending());
        Pageable page = PageRequest.of(pageNumber, pageSize,sort);
        Page<Category> getpage = categoryRepo.findAll(page);

        PageableResponse<CategoryDto> pagebleResponse = Sort_Helper.getPagebleResponse(getpage, CategoryDto.class);

        return pagebleResponse;
    }

    @Override
    public CategoryDto getSingleCategory(Long categoryId) {

        Category singleCat = categoryRepo.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));

        CategoryDto singleCategory = this.modelMapper.map(singleCat, CategoryDto.class);
        return singleCategory;
    }

    @Override
    public List<CategoryDto> searchCategory(String keyword) {

        List<Category> categories = categoryRepo.findByTitleContaining(keyword);

        List<CategoryDto> searchCat = categories.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
        return searchCat;
    }
}
