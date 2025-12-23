package com.jeeva.Smart.Library.Management.controller;


import com.jeeva.Smart.Library.Management.dto.book.Request.CreateBookRequest;
import com.jeeva.Smart.Library.Management.dto.book.Response.BookResponse;
import com.jeeva.Smart.Library.Management.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/library")
public class BookController {

    private final BookService bookService;


    @PostMapping("/create")
    public ResponseEntity<BookResponse> createBook (@RequestBody @Valid CreateBookRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBooks(request));

    }

    @PatchMapping("/addcopies")
    public ResponseEntity<String> addCopies (@RequestBody Map<String, Object> body) {
        String isbn = (String) body.get("isbn");
        Integer copies = (Integer) body.get("copies");
        return ResponseEntity.status(HttpStatus.OK).body(bookService.addCopies(isbn, copies));
    }

    @PatchMapping("/libdamage")
    public ResponseEntity<String> libraryDamage (@RequestBody Map<String,Object> body){
        String isbn= (String) body.get("isbn");
        Integer damaged=(Integer) body.get("damaged");
        return ResponseEntity.status(HttpStatus.OK).body(bookService.damagedLibraryBook(isbn, damaged));
    }

    @PatchMapping("/borrowdamage")
    public ResponseEntity<String> borrowDamage (@RequestBody Map<String,Object> body){
        String isbn= (String) body.get("isbn");
        Integer damaged=(Integer) body.get("damaged");

        return ResponseEntity.status(HttpStatus.OK).body(bookService.damagedBorrowBook(isbn, damaged));
    }


    @GetMapping("/allbooks")
    public ResponseEntity<Page<BookResponse>> allBooks(@RequestParam int page, @RequestParam int size){

        return ResponseEntity.status(HttpStatus.OK).body(bookService.allBooks(page,size));

    }

    @GetMapping("/getbook")
    public ResponseEntity<BookResponse> getBook (@RequestParam String isbn){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.viewBook(isbn));
    }




}
