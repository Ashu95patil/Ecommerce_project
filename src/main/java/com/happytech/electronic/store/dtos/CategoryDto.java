package com.happytech.electronic.store.dtos;

import com.happytech.electronic.store.config.AppConstants;
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
public class CategoryDto extends BaseEntityDto {


    private Long categoryId;

    @NotBlank(message = AppConstants.TITLE_MESSAGE)
    @Size(min = 4, message = AppConstants.TITLE_SIZE_MSG)
    private String title;

    @NotBlank(message = AppConstants.DESCRIPTION_MSG)
    private String description;

    @NotBlank(message = AppConstants.COVERIMAGE_MSG)
    @ImageNameValid
    private String coverImage;
}
