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
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Value(AppConstants.CATEGORY_IMAGE_PATH)
    private String imageUploadPath;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        log.info("Initiating request for save user to dao call {} ", categoryDto);


        Category category = this.modelMapper.map(categoryDto, Category.class);

        category.setIsactive(AppConstants.YES);

        Category saveCat = categoryRepo.save(category);

        CategoryDto savecategoryDto = this.modelMapper.map(saveCat, CategoryDto.class);

        log.info("completed request for saved user from dao call {} ", savecategoryDto);

        return savecategoryDto;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
        log.info("Initiating request for update user to dao call {} ", categoryDto);
        log.info("Initiating request for update user to dao call {} ", categoryId);

        //get category of given id
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));

        //update category
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());

        Category Cat = categoryRepo.save(category);

        CategoryDto updatedCat = this.modelMapper.map(Cat, CategoryDto.class);
        log.info("completed request for updated user from dao call {} ", updatedCat);

        return updatedCat;
    }

    @Override
    public void deleteCategory(Long categoryId) {
        log.info("Initiating request for delete user to dao call {} ", categoryId);

        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));

        //delete category profile image
        //images/category/abc.png  (is tarah path milelga)

        String fullPath = imageUploadPath + category.getCoverImage();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        } catch (NoSuchFileException ex) {
            log.info("Category image not found in folder....!!");
            ex.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }

        category.setIsactive(AppConstants.NO);

        categoryRepo.delete(category);
        log.info("completed request for deleted user from dao call {} ", category);


    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Initiating request for getAllUser user to dao call ");

        Sort sort = (sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending());
        Pageable page = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> getpage = categoryRepo.findAll(page);

        getpage.stream().filter(c -> c.getIsactive().equals(AppConstants.YES)).collect(Collectors.toList());

        PageableResponse<CategoryDto> pagebleResponse = Sort_Helper.getPagebleResponse(getpage, CategoryDto.class);
        log.info("completed request for getAllUser user from dao call {} ", pagebleResponse);

        return pagebleResponse;
    }

    @Override
    public CategoryDto getSingleCategory(Long categoryId) {
        log.info("Initiating request for getSingleUser user to dao call {} ", categoryId);

        Category singleCat = categoryRepo.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(AppConstants.CATEGORY, AppConstants.CATEGORY_ID, categoryId));

        CategoryDto singleCategory = this.modelMapper.map(singleCat, CategoryDto.class);
        log.info("completed request for getSingleUser user from dao call {} ", singleCategory);

        return singleCategory;
    }

    @Override
    public List<CategoryDto> searchCategory(String keyword) {
        log.info("Initiating request for get keyword to dao call {} ", keyword);

        List<Category> categories = categoryRepo.findByTitleContaining(keyword);

        List<CategoryDto> searchCat = categories.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
        log.info("completed request for get keyword  from dao call {} ", searchCat);

        return searchCat;
    }
}
