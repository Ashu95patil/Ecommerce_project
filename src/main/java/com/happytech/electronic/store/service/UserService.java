package com.happytech.electronic.store.service;

import com.happytech.electronic.store.dtos.PageableResponse;
import com.happytech.electronic.store.dtos.UserDto;

import java.util.List;

public interface UserService {

    // create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto userDto, Long userId);

    //delete
    void deleteUser(Long userId);

    //get all users
    PageableResponse<UserDto> getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //get single users
    UserDto getSingleUser(Long userId);


    //get single user by email
    UserDto getUserByEmail(String email);

    //search user
    List<UserDto> searchUser(String keyword);

    // other user specific features
}
