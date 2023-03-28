package com.happytech.electronic.store.service.impl;



import com.happytech.electronic.store.config.AppConstants;
import com.happytech.electronic.store.dtos.PageableResponse;
import com.happytech.electronic.store.dtos.UserDto;
import com.happytech.electronic.store.exception.EmailNotFoundException;
import com.happytech.electronic.store.exception.UserNotFoundException;
import com.happytech.electronic.store.helper.Sort_Helper;
import com.happytech.electronic.store.model.User;
import com.happytech.electronic.store.repository.UserRepo;
import com.happytech.electronic.store.service.UserService;
;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import java.util.stream.Collectors;


@Service
@Slf4j  //use for logger
public class UserServiceImpl implements UserService  {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${user.profile.image.path}")
    private String imagePath;


    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("Initiating Dao call for save user");

        //dto -> entity
        User user = dtoToEntity(userDto);

        user.setIsactive(AppConstants.YES);

        User savedUser = userRepo.save(user);

        //entity -> dto
        UserDto newDto = entityToDto(savedUser);

        log.info("Completed Dao call for save user");

        return newDto;
    }

    private UserDto entityToDto(User savedUser) {

        UserDto userDto = this.modelMapper.map(savedUser, UserDto.class);


//        UserDto userDto = UserDto.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .gender(savedUser.getGender())
//                .about(savedUser.getAbout())
//                .imageName(savedUser.getImageName()).build();

        return userDto;
    }

    private User dtoToEntity(UserDto userDto) {

        User user = this.modelMapper.map(userDto, User.class);
//        User user = User.builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .gender(userDto.getGender())
//                .about(userDto.getAbout())
//                .imageName(userDto.getImageName()).build();

        return user;

    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {

        log.info("Initiating Dao call for update user");

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(AppConstants.USER, AppConstants.USER_ID, userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setAbout(userDto.getAbout());
        user.setImageName(userDto.getImageName());

        User updateUser = this.userRepo.save(user);
        UserDto userDto1 = this.entityToDto(updateUser);

        log.info("Completed Dao call for update user");

        return userDto1;
    }

    @Override
    public void deleteUser(Long userId) {

        log.info("Initiating Dao call for delete user");

        User deleteUser = this.userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(AppConstants.USER, AppConstants.USER_ID, userId));

        //delete user profile image
        //images/users/abc.png  (is tarah path milelga)

        String fullPath = deleteUser.getImageName();
        try
        {
            Path path= Paths.get(fullPath);
            Files.delete(path);
        }catch (NoSuchFileException ex){

            log.info("User image not found in folder...!!");
            ex.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }


        deleteUser.setIsactive(AppConstants.NO);

        userRepo.save(deleteUser);
        log.info("Completed Dao call for delete user");

    }

    @Override
    public PageableResponse<UserDto> getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        log.info("Initiating Dao call for getAll user");

        Sort sort = (sortDir.equalsIgnoreCase(AppConstants.SORT_DIR) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<User> page = this.userRepo.findAll(pageable);

        page.stream().filter(u -> u.getIsactive().equals(AppConstants.YES)).collect(Collectors.toList());

        PageableResponse<UserDto> pageableResponse = Sort_Helper.getPagebleResponse(page, UserDto.class);

        //  List<UserDto> allUsers = collect.stream().map((u) -> this.modelMapper.map(u, UserDto.class)).collect(Collectors.toList());


        log.info("Completed Dao call for getAll user");

        return pageableResponse;
    }

    @Override
    public UserDto getSingleUser(Long userId) {

        log.info("Initiating Dao call for getSingle user");

        User singleUser = this.userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(AppConstants.USER, AppConstants.USER_ID, userId));

        UserDto singleUserDto = this.entityToDto(singleUser);
        log.info("Completed Dao call for getSingle user");

        return singleUserDto;
    }


    @Override
    public UserDto getUserByEmail(String email) {

        log.info("Initiating Dao call for getByEmail user");

        User userEmail = this.userRepo.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException((AppConstants.USER_EMAIL)));

        log.info("Completed Dao call for getByEmail user");

        return this.entityToDto(userEmail);


    }

    @Override
    public List<UserDto> searchUser(String keyword) {

        log.info("Initiating Dao call for search user");

        List<User> users = userRepo.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream()
                .map((user) -> this.modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());

        log.info("Completed Dao call for search user");
        return dtoList;
    }
}

