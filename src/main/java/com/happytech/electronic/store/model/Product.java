package com.happytech.electronic.store.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "products")
public class Product extends BaseEntityClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(name = "product_title", nullable = false)
    private String title;
    @Column(name = "product_description", length = 1000)
    private String description;
    @Column(name = "product_price")
    private Double price;
    @Column(name = "product_discountprice")
    private Double discountedPrice;
    @Column(name = "product_sku", length = 50, nullable = false)
    private String sku;    //stock keeping unit
    @Column(name = "product_quatity")
    private Integer quantity;
    private Integer quantityInStock;
    @Column(name = "product_stock")
    private boolean stock;
    @Column(name = "product_brandName")
    private String brand;
    //  private String imageUrl;
    @Column(name = "product_rating")
    private Double rating;

    @Column(name = "product_live")
    private boolean live;

}
