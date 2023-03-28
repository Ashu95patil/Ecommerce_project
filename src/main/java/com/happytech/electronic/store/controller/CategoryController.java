package com.happytech.electronic.store.controller;

import antlr.StringUtils;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;

    @Value("${category.profile.image.path}")
    private String imageUploadPath;

    //create

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {

        CategoryDto saveCat = categoryService.createCategory(categoryDto);

        return new ResponseEntity<>(saveCat, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Long categoryId) {

        CategoryDto updateCat = categoryService.updateCategory(categoryDto, categoryId);

        return new ResponseEntity<>(updateCat, HttpStatus.CREATED);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> detetCategory(@PathVariable Long categoryId) {

        categoryService.deleteCategory(categoryId);

        ApiResponse deletedCat = ApiResponse.builder().message(AppConstants.CATEGORY_DELETE + categoryId).success(true).status(HttpStatus.OK).build();

        return new ResponseEntity<>(deletedCat, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(@RequestParam(value = AppConstants.NO_VALUE, defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                        @RequestParam(value = AppConstants.SIZE_VALUE, defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                        @RequestParam(value = AppConstants.BY_VALUE, defaultValue = AppConstants.SORT_BYCAT, required = false) String sortBy,
                                                                        @RequestParam(value = AppConstants.DIR_VALUE, defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        PageableResponse<CategoryDto> allCategory = categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(allCategory, HttpStatus.OK);

    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Long categoryId) {

        CategoryDto singleCategory = categoryService.getSingleCategory(categoryId);

        return new ResponseEntity<>(singleCategory, HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<CategoryDto>> searchCategory(@PathVariable String keyword) {

        List<CategoryDto> categoryDtos = categoryService.searchCategory(keyword);

        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);


    }

    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadImageCategory(@RequestParam("categoryImage") MultipartFile image, @PathVariable Long categoryId) throws IOException {

        String imageName = fileService.uploadFile(image, imageUploadPath);

        CategoryDto singleCategory = categoryService.getSingleCategory(categoryId);

        singleCategory.setCoverImage(imageName);
        CategoryDto categoryDto = categoryService.updateCategory(singleCategory, categoryId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).message(AppConstants.CATEGORY_FILE_UPLOADED).success(true).status(HttpStatus.OK).build();

        return new ResponseEntity<>(imageResponse, HttpStatus.OK);
    }

    @GetMapping("/images/{categoryId}")
    public void serveImageCategory(@PathVariable Long categoryId, HttpServletResponse response) throws IOException {

        CategoryDto singleCategory = categoryService.getSingleCategory(categoryId);

        log.info("category image name : {} ", singleCategory.getCoverImage());
        InputStream resource = fileService.getResource(imageUploadPath, singleCategory.getCoverImage());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());


    }
}
