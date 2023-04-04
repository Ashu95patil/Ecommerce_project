package com.happytech.electronic.store.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductDto extends BaseEntityDto{

    private Long productId;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    @DecimalMin("0.0")
    private Double price;
    @DecimalMin("0.0")
    private Double discountedPrice;
    @NotBlank
    private String sku;    //stock keeping unit
    @NotNull
    @Min(0)
    private Integer quantity;
    @NotNull
    @Min(0)
    private Integer quantityInStock;
    private boolean stock;
    @NotBlank
    private String brand;
    //  private String imageUrl;
    @DecimalMin("0.0")
    @Max(5)
    private Double rating;

    private boolean live;
}
