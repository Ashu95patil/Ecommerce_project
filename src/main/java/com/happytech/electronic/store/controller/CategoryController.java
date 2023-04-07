package com.happytech.electronic.store.controller;

import com.happytech.electronic.store.config.AppConstants;
import com.happytech.electronic.store.dtos.ApiResponse;
import com.happytech.electronic.store.dtos.CategoryDto;
import com.happytech.electronic.store.dtos.ImageResponse;
import com.happytech.electronic.store.dtos.PageableResponse;
import com.happytech.electronic.store.service.CategoryService;
import com.happytech.electronic.store.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping(AppConstants.CATEGORY_URL)
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;

    @Value(AppConstants.CATEGORY_IMAGE_PATH)
    private String imageUploadPath;

    //create

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {

        log.info("Entering into the method : this is the categoryDto {} ", categoryDto);

        CategoryDto saveCat = categoryService.createCategory(categoryDto);

        log.info("Exiting into the method : this is the saveCat of categoryDto  {} ", saveCat);

        return new ResponseEntity<>(saveCat, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Long categoryId) {

        log.info("Entering into the method : this is the categoryDto {} ", categoryDto);

        log.info("Entering into the method : this is the update categoryId {} ", categoryId);

        CategoryDto updateCat = categoryService.updateCategory(categoryDto, categoryId);

        log.info("Exiting into the method : this is the updateCat of categoryId  {} ", updateCat);

        return new ResponseEntity<>(updateCat, HttpStatus.CREATED);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> detetCategory(@PathVariable Long categoryId) {

        log.info("Entering into the method : this is the delete categoryId {} ", categoryId);

        categoryService.deleteCategory(categoryId);

        ApiResponse deletedCat = ApiResponse.builder().message(AppConstants.CATEGORY_DELETE + categoryId).success(true).status(HttpStatus.OK).build();

        log.info("Exiting into the method : this is the deletedCat of categoryId  {} ", deletedCat);

        return new ResponseEntity<>(deletedCat, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(@RequestParam(value = AppConstants.NO_VALUE, defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                        @RequestParam(value = AppConstants.SIZE_VALUE, defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                        @RequestParam(value = AppConstants.BY_VALUE, defaultValue = AppConstants.SORT_BYCAT, required = false) String sortBy,
                                                                        @RequestParam(value = AppConstants.DIR_VALUE, defaultValue = AppConstants.SORT_DIR_DESC, required = false) String sortDir) {

        log.info("Entering into the method : this is the CategoryDto ");

        PageableResponse<CategoryDto> allCategory = categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);

        log.info("Exiting into the method : this is the allCategory of CategoryDto  {} ", allCategory);

        return new ResponseEntity<>(allCategory, HttpStatus.OK);

    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Long categoryId) {

        log.info("Entering into the method : this is the get categoryId {} ", categoryId);

        CategoryDto singleCategory = categoryService.getSingleCategory(categoryId);

        log.info("Exiting into the method : this is the singleCategory of categoryId  {} ", singleCategory);

        return new ResponseEntity<>(singleCategory, HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<CategoryDto>> searchCategory(@PathVariable String keyword) {

        log.info("Entering into the method : this is the keyword {} ", keyword);

        List<CategoryDto> categoryDtos = categoryService.searchCategory(keyword);

        log.info("Exiting into the method : this is the categoryDtos of keyword  {} ", categoryDtos);

        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);


    }

    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadImageCategory(@RequestParam("categoryImage") MultipartFile image, @PathVariable Long categoryId) throws IOException {

        log.info("Entering into the method : this is the image {} ", image);
        log.info("Entering into the method : this is the upload image categoryId {} ", categoryId);


        String imageName = fileService.uploadFile(image, imageUploadPath);

        CategoryDto singleCategory = categoryService.getSingleCategory(categoryId);

        singleCategory.setCoverImage(imageName);
        CategoryDto categoryDto = categoryService.updateCategory(singleCategory, categoryId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).message(AppConstants.CATEGORY_FILE_UPLOADED).success(true).status(HttpStatus.OK).build();

        log.info("Exiting into the method : this is the imageResponse of image  {} ", imageResponse);

        return new ResponseEntity<>(imageResponse, HttpStatus.OK);
    }

    @GetMapping("/images/{categoryId}")
    public void serveImageCategory(@PathVariable Long categoryId, HttpServletResponse response) throws IOException {

        log.info("Entering into the method : this is the save image categoryId {} ", categoryId);

        CategoryDto singleCategory = categoryService.getSingleCategory(categoryId);

        log.info("category image name : {} ", singleCategory.getCoverImage());
        InputStream resource = fileService.getResource(imageUploadPath, singleCategory.getCoverImage());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());
        log.info("Exiting into the method : this is the resource of categoryId  {} ", resource);


    }
}
