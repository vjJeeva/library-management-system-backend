package com.jeeva.Smart.Library.Management.service;

import com.jeeva.Smart.Library.Management.dto.borrow.Request.BorrowBookRequest;
import com.jeeva.Smart.Library.Management.dto.borrow.Request.ReturnBorrowBookRequest;
import com.jeeva.Smart.Library.Management.dto.borrow.Response.BorrowRecordResponse;
import com.jeeva.Smart.Library.Management.enums.BorrowStatus;
import com.jeeva.Smart.Library.Management.enums.MemberStatus;
import com.jeeva.Smart.Library.Management.mapper.BorrowRecordMapper;
import com.jeeva.Smart.Library.Management.model.Book;
import com.jeeva.Smart.Library.Management.model.BorrowRecord;
import com.jeeva.Smart.Library.Management.model.Member;
import com.jeeva.Smart.Library.Management.repository.BookRepository;
import com.jeeva.Smart.Library.Management.repository.BorrowRecordRepository;
import com.jeeva.Smart.Library.Management.repository.MemberRepository;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BorrowServiceTest {

    @Mock
    private BorrowRecordRepository borrowRecordRepository ;

    @Mock
    private BorrowRecordMapper borrowRecordMapper;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BorrowService borrowService;

    @Test
    void borrow_Success() {

        BorrowBookRequest request= new BorrowBookRequest(1L,1L);

        Member member = new Member();
        member.setStatus(MemberStatus.ACTIVE);

        Book book = new Book();
        book.setAvailableCopies(5);

        BorrowRecord record = new BorrowRecord();
        BorrowRecordResponse response = new BorrowRecordResponse();

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowRecordMapper.toEntity(request)).thenReturn(record);
        when(borrowRecordRepository.save(any())).thenReturn(record);
        when(borrowRecordMapper.toResponse(any())).thenReturn(response);

        BorrowRecordResponse result = borrowService.borrow(request);

        assertNotNull(result);
        assertEquals(4,book.getAvailableCopies());
        verify(borrowRecordRepository,times(1)).save(any());
    }

    @Test
    void returnBook() {
        ReturnBorrowBookRequest request = new ReturnBorrowBookRequest(100L);


        Book book = new Book();
        book.setAvailableCopies(4);

        BorrowRecord record= new BorrowRecord();

        record.setStatus(BorrowStatus.BORROWED);
        record.setBook(book);

        when(borrowRecordRepository.findById(100L)).thenReturn(Optional.of(record));

        borrowService.returnBook(request);

        assertEquals(BorrowStatus.RETURNED, record.getStatus());
        assertEquals(5,book.getAvailableCopies());



    }

    @Test
    void getBorrowListing_Success() {
        // 1. Arrange
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by("borrowDate").descending());

        // Create a fake list with one record
        BorrowRecord record = new BorrowRecord();
        Page<BorrowRecord> recordPage = new PageImpl<>(List.of(record));

        BorrowRecordResponse response = new BorrowRecordResponse();

        // Tell the mocks what to do
        when(borrowRecordRepository.findByStatus(eq(BorrowStatus.BORROWED), any(Pageable.class)))
                .thenReturn(recordPage);
        when(borrowRecordMapper.toResponse(any())).thenReturn(response);

        // 2. Act
        Page<BorrowRecordResponse> result = borrowService.getBorrowListing(page, size);

        // 3. Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(borrowRecordRepository).findByStatus(any(), any());
    }

    @Test
    void borrowHistory_Success() {
        // 1. Arrange
        Long memberId = 1L;
        Member member = new Member();
        member.setId(memberId);

        BorrowRecord record1 = new BorrowRecord();
        BorrowRecord record2 = new BorrowRecord();
        List<BorrowRecord> records = List.of(record1, record2);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(borrowRecordRepository.findByMember(member)).thenReturn(records);
        when(borrowRecordMapper.toResponse(any())).thenReturn(new BorrowRecordResponse());

        // 2. Act
        List<BorrowRecordResponse> result = borrowService.borrowHistory(memberId);

        // 3. Assert
        assertEquals(2, result.size());
        verify(borrowRecordRepository).findByMember(member);
    }
}