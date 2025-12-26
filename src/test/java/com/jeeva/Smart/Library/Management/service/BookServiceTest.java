package com.jeeva.Smart.Library.Management.service;

import com.jeeva.Smart.Library.Management.dto.book.Request.CreateBookRequest;
import com.jeeva.Smart.Library.Management.dto.book.Response.BookResponse;
import com.jeeva.Smart.Library.Management.exception.ResourceNotFoundException;
import com.jeeva.Smart.Library.Management.mapper.BookMapper;
import com.jeeva.Smart.Library.Management.model.Book;
import com.jeeva.Smart.Library.Management.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    // --- addBooks Tests ---

    @Test
    void addBooks_Success() {
        CreateBookRequest request = new CreateBookRequest();
        request.setIsbn("123");
        Book book = new Book();

        when(bookRepository.findByIsbn("123")).thenReturn(Optional.empty());
        when(bookMapper.toEntity(request)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toResponse(book)).thenReturn(new BookResponse());

        assertNotNull(bookService.addBooks(request));
        verify(bookRepository).save(any());
    }

    @Test
    void addBooks_Failure_AlreadyExists() {
        CreateBookRequest request = new CreateBookRequest();
        request.setIsbn("123");
        when(bookRepository.findByIsbn("123")).thenReturn(Optional.of(new Book()));

        assertThrows(RuntimeException.class, () -> bookService.addBooks(request));
    }

    // --- addCopies Tests ---

    @Test
    void addCopies_Success() {
        Book book = new Book();
        book.setTotalCopies(10);
        book.setAvailableCopies(10);

        when(bookRepository.findByIsbn("123")).thenReturn(Optional.of(book));

        bookService.addCopies("123", 5);

        assertEquals(15, book.getTotalCopies());
        assertEquals(15, book.getAvailableCopies());
    }

    @Test
    void addCopies_Failure_InvalidCount() {
        assertThrows(IllegalArgumentException.class, () -> bookService.addCopies("123", 0));
    }

    // --- damagedLibraryBook Tests (Books on shelf) ---

    @Test
    void damagedLibraryBook_Success() {
        Book book = new Book();
        book.setTotalCopies(10);
        book.setAvailableCopies(10);

        when(bookRepository.findByIsbn("123")).thenReturn(Optional.of(book));

        bookService.damagedLibraryBook("123", 3);

        assertEquals(7, book.getTotalCopies());
        assertEquals(7, book.getAvailableCopies());
    }

    @Test
    void damagedLibraryBook_Failure_NotEnoughAvailable() {
        Book book = new Book();
        book.setAvailableCopies(2);
        when(bookRepository.findByIsbn("123")).thenReturn(Optional.of(book));

        assertThrows(RuntimeException.class, () -> bookService.damagedLibraryBook("123", 5));
    }

    // --- damagedBorrowBook Tests (Books with members) ---

    @Test
    void damagedBorrowBook_Success() {
        Book book = new Book();
        book.setTotalCopies(10);
        book.setAvailableCopies(8); // 2 are borrowed

        when(bookRepository.findByIsbn("123")).thenReturn(Optional.of(book));

        bookService.damagedBorrowBook("123", 1);

        assertEquals(9, book.getTotalCopies());
        assertEquals(8, book.getAvailableCopies()); // Available stays 8 because the damaged one wasn't there!
    }

    @Test
    void damagedBorrowBook_Failure_NoBorrowedBooks() {
        Book book = new Book();
        book.setTotalCopies(5);
        book.setAvailableCopies(5); // 0 borrowed

        when(bookRepository.findByIsbn("123")).thenReturn(Optional.of(book));

        assertThrows(RuntimeException.class, () -> bookService.damagedBorrowBook("123", 1));
    }

    // --- viewBook Tests ---

    @Test
    void viewBook_Success() {
        Book book = new Book();
        book.setIsbn("123");
        when(bookRepository.findByIsbn("123")).thenReturn(Optional.of(book));
        when(bookMapper.toResponse(book)).thenReturn(new BookResponse());

        assertNotNull(bookService.viewBook("123"));
    }

    @Test
    void viewBook_Failure_NotFound() {
        when(bookRepository.findByIsbn("456")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> bookService.viewBook("456"));
    }

    // --- allBooks Tests ---

    @Test
    void allBooks_Success() {
        Page<Book> page = new PageImpl<>(List.of(new Book()));
        when(bookRepository.findAll(any(Pageable.class))).thenReturn(page);

        var result = bookService.allBooks(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }
}