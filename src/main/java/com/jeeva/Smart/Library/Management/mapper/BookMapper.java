package com.jeeva.Smart.Library.Management.mapper;


import com.jeeva.Smart.Library.Management.dto.book.Request.CreateBookRequest;
import com.jeeva.Smart.Library.Management.dto.book.Response.BookResponse;
import com.jeeva.Smart.Library.Management.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "availableCopies", expression = "java(request.getTotalCopies())")
    Book toEntity (CreateBookRequest request);

    @Mapping(target = "borrowedCopies")
    BookResponse toResponse (Book book);
}
