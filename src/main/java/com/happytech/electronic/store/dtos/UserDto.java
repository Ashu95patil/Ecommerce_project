package com.happytech.electronic.store.dtos;

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
   @Size(min = 4,max = 20,message = "username must be min 4 characters..!!")
    private String name;
   @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message = "Invalid EmailId...!!")
    @NotBlank(message = "Email is required...!!")
   private String email;

  // @Pattern(regexp = "^(?=.[a-z])(?=.[A-Z])(?=.\\d)(?=.[#$@!%&?])[A-Za-z\\d#$@!%&?]{8,}$")
   @NotBlank(message = "Password is required..!!")
    private String password;
    @Size(min = 4, max = 6,message = "Invalid gender..!!")
    private String gender;

    @NotBlank(message = "Write something about yourself..!!")
    private String about;

    //Custome validator
    @ImageNameValid
    private String imageName;



}
