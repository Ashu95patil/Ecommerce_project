package com.happytech.electronic.store.dtos;

import com.happytech.electronic.store.config.AppConstants;
import com.happytech.electronic.store.model.BaseEntityClass;
import com.happytech.electronic.store.validate.ImageNameValid;
import lombok.*;

import javax.validation.constraints.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends BaseEntityDto {


    private Long userId;

    //  @NotEmpty
    @Size(min = 4, max = 20, message = AppConstants.NAME_SIZE)
    private String name;
    @Pattern(regexp = AppConstants.EMAIL_REGEXP, message = "Invalid EmailId...!!")
    @NotBlank(message = AppConstants.EMAIL_MSG)
    private String email;

    // @Pattern(regexp = "^(?=.[a-z])(?=.[A-Z])(?=.\\d)(?=.[#$@!%&?])[A-Za-z\\d#$@!%&?]{8,}$")
    @NotBlank(message = AppConstants.PASSWORD_MSG)
    private String password;
    @Size(min = 4, max = 6, message = AppConstants.GENDER_MSG)
    private String gender;

    @NotBlank(message = AppConstants.ABOUT_MSG)
    private String about;

    //Custome validator
    @ImageNameValid
    private String imageName;


}
