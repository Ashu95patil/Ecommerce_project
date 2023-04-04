package com.happytech.electronic.store.model;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "categories")
public class Category extends BaseEntityClass{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    private Long categoryId;
    @Column(name = "category_title", length = 60, nullable = false)
    private String title;
    @Column(name = "category_desc", length = 500)
    private String description;

    private String coverImage;


}
