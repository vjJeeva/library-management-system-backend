package com.jeeva.Smart.Library.Management.service;

import com.jeeva.Smart.Library.Management.dto.book.Request.CreateBookRequest;
import com.jeeva.Smart.Library.Management.dto.book.Response.BookResponse;
import com.jeeva.Smart.Library.Management.exception.ResourceNotFoundException;
import com.jeeva.Smart.Library.Management.mapper.BookMapper;
import com.jeeva.Smart.Library.Management.model.Book;
import com.jeeva.Smart.Library.Management.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Transactional
    public BookResponse addBooks (CreateBookRequest request){
        if(bookRepository.findByIsbn(request.getIsbn()).isPresent()){
            throw new RuntimeException("book already exists");
        }
        Book book=bookMapper.toEntity(request);

        Book bookSaved=bookRepository.save(book);
        return bookMapper.toResponse(bookSaved);
    }

    @Transactional
    public String addCopies (String isbn, Integer copies){
        if (copies <= 0) {
            throw new IllegalArgumentException("Copies must be greater than zero");
        }

        Book book= bookRepository.findByIsbn(isbn).orElseThrow(()-> new ResourceNotFoundException("Book doesn't exist"));

        book.setTotalCopies(book.getTotalCopies()+copies);
        book.setAvailableCopies(book.getAvailableCopies()+copies);

        return "Copies added successfully";
    }

    @Transactional
    public String damagedLibraryBook (String isbn,Integer damaged){
        if (damaged <= 0) {
            throw new IllegalArgumentException("Copies must be greater than zero");
        }

        Book book=bookRepository.findByIsbn(isbn).orElseThrow(()-> new ResourceNotFoundException("BOok doesn't exist"));

        if (book.getAvailableCopies() < damaged) {
            throw new RuntimeException("Not enough available copies to remove");
        }

        book.setTotalCopies(book.getTotalCopies()-damaged);
        book.setAvailableCopies(book.getAvailableCopies()-damaged);

        return "successfully removed";
    }

    @Transactional
    public String damagedBorrowBook (String isbn,Integer damaged){
        if (damaged <= 0) {
            throw new IllegalArgumentException("Copies must be greater than zero");
        }

        Book book=bookRepository.findByIsbn(isbn).orElseThrow(()-> new ResourceNotFoundException("Book doesn't exist"));

        if ((book.getTotalCopies()- book.getAvailableCopies()) < damaged) {
            throw new RuntimeException("Not enough borrowed copies to remove");
        }

        book.setTotalCopies(book.getTotalCopies()-damaged);
        return "successfully removed";
    }

    public Page<BookResponse> allBooks (int page, int size){

        Pageable pageable= PageRequest.of(page,size, Sort.by("title").ascending());
        return bookRepository.findAll(pageable).map(bookMapper::toResponse);
    }

    public BookResponse viewBook(String isbn){
        Book book=bookRepository.findByIsbn(isbn).orElseThrow(()-> new ResourceNotFoundException("BOok doesn't exist"));
        return (bookMapper.toResponse(book));
    }
}
