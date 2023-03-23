package com.happytech.electronic.store.helper;

import com.happytech.electronic.store.dtos.PageableResponse;
import com.happytech.electronic.store.dtos.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Sort_Helper {


    public static <U,V>PageableResponse<V> getPagebleResponse(Page<U> page,Class<V> type){

        List<U> entity = page.getContent();
        List<V> userDtoList = entity.stream().map(user -> new ModelMapper().map(user, type)).collect(Collectors.toList());
        PageableResponse<V> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(userDtoList);
        pageableResponse.setPageNumber(page.getNumber());
        pageableResponse.setPageSize(page.getSize());
        pageableResponse.setTotalElements(page.getTotalElements());
        pageableResponse.setTotalPages(page.getTotalPages());
        pageableResponse.setLastPage(page.isLast());

        return  pageableResponse;

    }
}
