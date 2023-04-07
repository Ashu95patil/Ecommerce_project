package com.happytech.electronic.store.controller;

import com.happytech.electronic.store.config.AppConstants;
import com.happytech.electronic.store.dtos.ApiResponse;
import com.happytech.electronic.store.dtos.ImageResponse;
import com.happytech.electronic.store.dtos.PageableResponse;
import com.happytech.electronic.store.dtos.UserDto;
import com.happytech.electronic.store.service.FileService;
import com.happytech.electronic.store.service.UserService;
import com.happytech.electronic.store.validate.ImageNameValid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(AppConstants.USER_URL)
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value(AppConstants.USER_IMAGE_PATH)
    private String imageUploadPath;


    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Entering into the method : this is the userDto {} ",userDto);

        UserDto saveUser = userService.createUser(userDto);
        log.info("Exiting into the method : this is the newDto of userDto  {} ", saveUser);


        return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long userId) {

        log.info("Entering into the method : this is the userDto {} ",userDto);

        log.info("Entering into the method : this is the userId {} ",userId);


        UserDto updateUser = userService.updateUser(userDto, userId);

        log.info("Exiting into the method : this is the updateUser of userDto  {} ",updateUser);

        return new ResponseEntity<>(updateUser, HttpStatus.CREATED);

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {

        log.info("Entering into the method : this is the userId {} ",userId);

        userService.deleteUser(userId);

        ApiResponse apiResponse = ApiResponse.builder().message(AppConstants.USER_DELETE).success(true).status(HttpStatus.OK).build();

        log.info("Exiting into the method : this is the apiResponse of userId  {} ",apiResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(@RequestParam(value = AppConstants.NO_VALUE, defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                 @RequestParam(value = AppConstants.SIZE_VALUE, defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                 @RequestParam(value = AppConstants.BY_VALUE, defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                                 @RequestParam(value = AppConstants.DIR_VALUE, defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        log.info("Entering into the method : this is the userDto ");


        PageableResponse<UserDto> allUser = userService.getAllUser(pageNumber, pageSize, sortBy, sortDir);

        log.info("Exiting into the method : this is the allUser of userDto  {} ",allUser);
        return new ResponseEntity<>(allUser, HttpStatus.OK);

    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Long userId) {

        log.info("Entering into the method : this is the userId {} ",userId);

        UserDto singleUser = userService.getSingleUser(userId);

        log.info("Exiting into the method : this is the singleUser of userId  {} ",singleUser);
        return new ResponseEntity<>(singleUser, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {

        log.info("Entering into the method : this is the email {} ",email);

        UserDto userByEmail = userService.getUserByEmail(email);

        log.info("Exiting into the method : this is the userByEmail of email  {} ",userByEmail);

        return new ResponseEntity<>(userByEmail, HttpStatus.OK);


    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {

        log.info("Entering into the method : this is the keyword {} ",keyword);

        List<UserDto> searchUser = userService.searchUser(keyword);

        log.info("Exiting into the method : this is the searchUser of keyword  {} ",searchUser);
        return new ResponseEntity<>(searchUser, HttpStatus.OK);

    }


    //upload user Image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam("userImage") MultipartFile image,
                                                     @PathVariable Long userId) throws IOException {

        log.info("Entering into the method : this is the image {} ",image);
        log.info("Entering into the method : this is the userId {} ",userId);

        String imageName = fileService.uploadFile(image, imageUploadPath);
        UserDto singleUser = userService.getSingleUser(userId);

        singleUser.setImageName(imageName);
        UserDto userDto = userService.updateUser(singleUser, userId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).message(AppConstants.FILE_UPLOADED).success(true).status(HttpStatus.CREATED).build();

        log.info("Exiting into the method : this is the imageResponse of image  {} ",imageResponse);

        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }


    //serve user image
    @GetMapping("/image/{userId}")
    public void serveImage(@PathVariable Long userId, HttpServletResponse response) throws IOException {

        log.info("Entering into the method : this is the userId {} ",userId);


        UserDto singleUser = userService.getSingleUser(userId);

        log.info("user image name : {} ",singleUser.getImageName());
        InputStream resource = fileService.getResource(imageUploadPath, singleUser.getImageName());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

        log.info("Exiting into the method : this is the resource of userId  {} ",resource);
    }
}
