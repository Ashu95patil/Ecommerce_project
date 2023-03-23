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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
@Slf4j
@RestController
@RequestMapping(AppConstants.USER_URL)
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;


    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

        log.info("Initiating request for save user");
        UserDto saveUser = userService.createUser(userDto);

        log.info("Completed request for save user");
        return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long userId) {

        log.info("Initiating request for update user");

        UserDto updateUser = userService.updateUser(userDto, userId);

        log.info("Completed request for update user");
        return new ResponseEntity<>(updateUser, HttpStatus.CREATED);

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {

        log.info("Initiating request for delete user");

        userService.deleteUser(userId);

        ApiResponse apiResponse = ApiResponse.builder().message(AppConstants.USER_DELETE).success(true).status(HttpStatus.OK).build();

        log.info("Completed request for delete user");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(@RequestParam (value = AppConstants.NO_VALUE,defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                     @RequestParam(value = AppConstants.SIZE_VALUE,defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
                                                     @RequestParam(value = AppConstants.BY_VALUE,defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
                                                     @RequestParam(value = AppConstants.DIR_VALUE,defaultValue = AppConstants.SORT_DIR,required = false)String sortDir) {

        log.info("Initiating request for getAll user");

        PageableResponse<UserDto> allUser = userService.getAllUser(pageNumber, pageSize, sortBy, sortDir);

        log.info("Completed request for getAll user");
        return new ResponseEntity<>(allUser, HttpStatus.OK);

    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Long userId) {

        log.info("Initiating request for getSingle user");

        UserDto singleUser = userService.getSingleUser(userId);

        log.info("Completed request for getSingle user");

        return new ResponseEntity<>(singleUser, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {

        log.info("Initiating request for getByEmail user");

        UserDto userByEmail = userService.getUserByEmail(email);

        log.info("Completed request for getByEmail user");

        return new ResponseEntity<>(userByEmail, HttpStatus.OK);


    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {

        log.info("Initiating request for search user");

        List<UserDto> searchUser = userService.searchUser(keyword);

        log.info("Completed request for search user");

        return new ResponseEntity<>(searchUser, HttpStatus.OK);

    }


    //upload user Image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam("userImage")MultipartFile image,
                                                     @PathVariable Long userId) throws IOException {
        UserDto singleUser = userService.getSingleUser(userId);
        String imageName = fileService.uploadFile(image, imageUploadPath);
         userService.updateUser(singleUser, userId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).message(AppConstants.FILE_UPLOADED).success(true).status(HttpStatus.CREATED).build();
            return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }





    //serve user image
}
