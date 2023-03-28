package com.happytech.electronic.store.dtos;

import com.happytech.electronic.store.validate.ImageNameValid;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDto {


    private Long categoryId;

    @NotBlank(message = "title must be required....!!!")
    @Size(min = 4, message = "title must be of minimum 4 character...!!")
    private String title;

    @NotBlank(message = "Description required..!!")
    private String description;

    @NotBlank(message = "cover image required..!!")
    @ImageNameValid
    private String coverImage;
}
