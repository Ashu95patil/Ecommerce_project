package com.happytech.electronic.store.service;

import com.happytech.electronic.store.dtos.PageableResponse;
import com.happytech.electronic.store.dtos.UserDto;
import com.happytech.electronic.store.model.User;
import com.happytech.electronic.store.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {


    @MockBean
    private UserRepo userRepo;

    //@Autowired
    //  @InjectMocks
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;
    User user;


    @BeforeEach
    public void init() {

        user = User.builder()
                .name("Ashwini")
                .email("patilashu@gmail.com")
                .about("This is testing create method")
                .gender("Female")
                .imageName("abc.png")
                .password("abcdefgh")
                .build();
    }

    //create user test
    @Test
    public void createUserTest() {

        Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);


        UserDto user1 = userService.createUser(modelMapper.map(user, UserDto.class));

        System.out.println(user1.getName());
        Assertions.assertNotNull(user1);
        Assertions.assertEquals("Ashwini", user1.getName());
    }


    //update user test
    @Test
    public void updateUserTest() {

        Long userId = 1l;
        UserDto userDto = UserDto.builder()
                .name("Ashwini Patil")
                .about("This is testing updated user method")
                .gender("Female")
                .imageName("xyz.png")
                .password("123456")
                .build();

        Mockito.when(userRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);

        UserDto updatedUser = userService.updateUser(userDto, userId);

        // UserDto updatedUser = modelMapper.map(user, UserDto.class);
        System.out.println(updatedUser.getName());
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(userDto.getName(), updatedUser.getName(), "Name is not Validate");
        //multiple assertion are valid

    }

    //delete user test case
    @Test
    public void deleteUserTest() {

        Long userId = 1l;

        Mockito.when(userRepo.findById(1l)).thenReturn(Optional.of(user));

        userService.deleteUser(userId);
        Mockito.verify(userRepo, Mockito.times(1)).delete(user);


    }

    //get All user test case
    @Test
    public void getAllUserTest() {

        User user1 = User.builder()
                .name("Isha")
                .email("patilashu@gmail.com")
                .about("This is testing create method")
                .gender("Female")
                .imageName("abc.png")
                .password("abcdefgh")
                .build();
        User user2 = User.builder()
                .name("Amruta")
                .email("patilashu@gmail.com")
                .about("This is testing create method")
                .gender("Female")
                .imageName("abc.png")
                .password("abcdefgh")
                .build();

        List<User> userList = Arrays.asList(user, user1, user2);

        Page<User> page = new PageImpl<>(userList);

        Mockito.when(userRepo.findAll((Pageable) Mockito.any())).thenReturn(page);

        //Sort sort = Sort.by("name").ascending();
        //Pageable pageable = PageRequest.of(1,2,sort);
        PageableResponse<UserDto> allUser = userService.getAllUser(1, 2, "name", "asc");

        Assertions.assertEquals(3, allUser.getContent().size());
    }
}