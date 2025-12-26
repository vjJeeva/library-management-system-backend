package com.jeeva.Smart.Library.Management.controller;


import com.jeeva.Smart.Library.Management.dto.borrow.Request.BorrowBookRequest;
import com.jeeva.Smart.Library.Management.dto.borrow.Request.ReturnBorrowBookRequest;
import com.jeeva.Smart.Library.Management.dto.borrow.Response.BorrowRecordResponse;
import com.jeeva.Smart.Library.Management.service.BorrowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/library")
public class BorrowController {

    private final BorrowService borrowService;

    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBook (@RequestBody @Valid BorrowBookRequest request){
        BorrowRecordResponse borrowRecordResponse=borrowService.borrow(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(borrowRecordResponse);
    }

    @PostMapping("borrow/return")
    public ResponseEntity<?> returnBook (@RequestBody @Valid ReturnBorrowBookRequest request){
        borrowService.returnBook(request);
        return ResponseEntity.ok(Map.of("message", "Book successfully returned"));
    }

    @GetMapping("borrow/listing")
    public ResponseEntity<Page<BorrowRecordResponse>> borrowListing (@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){

        Page<BorrowRecordResponse> pageResult=borrowService.getBorrowListing(page, size);

        return ResponseEntity.status(HttpStatus.OK).body(pageResult);
    }

    @GetMapping("borrow/history/{id}")
    public ResponseEntity<List<BorrowRecordResponse>> getBorrowHistory (@PathVariable long id){
        return ResponseEntity.status(HttpStatus.OK).body(borrowService.borrowHistory(id));
    }
}



