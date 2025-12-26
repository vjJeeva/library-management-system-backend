package com.jeeva.Smart.Library.Management.mapper;


import com.jeeva.Smart.Library.Management.dto.borrow.Request.BorrowBookRequest;
import com.jeeva.Smart.Library.Management.dto.borrow.Response.BorrowRecordResponse;
import com.jeeva.Smart.Library.Management.model.BorrowRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BorrowRecordMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "member", ignore = true)
    @Mapping(target = "book", ignore = true)
    @Mapping(target = "returnDate", ignore = true)
    @Mapping(target = "borrowDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status" , constant = "BORROWED")
    BorrowRecord toEntity (BorrowBookRequest request);

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "member.id", target = "memberId")
    BorrowRecordResponse toResponse(BorrowRecord record);
}