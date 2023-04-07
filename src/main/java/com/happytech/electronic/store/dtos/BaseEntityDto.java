package com.happytech.electronic.store.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntityDto {

    private LocalDate createdate;


    private LocalDate updatedate;


    private String isactive;
}
