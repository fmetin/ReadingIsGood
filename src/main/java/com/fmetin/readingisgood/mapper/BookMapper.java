package com.fmetin.readingisgood.mapper;

import com.fmetin.readingisgood.dto.CreateBookRequestDto;
import com.fmetin.readingisgood.entity.Book;
import com.fmetin.readingisgood.shared.LocalDateTimeUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class BookMapper {

    @Autowired
    protected LocalDateTimeUtil localDateTimeUtil;

    @Mapping(target = "createdDate", expression = "java(localDateTimeUtil.now())")
    @Mapping(target = "updatedDate", expression = "java(localDateTimeUtil.now())")
    public abstract Book mapCreateBookRequestDtoToBook(CreateBookRequestDto requestDto);

}
