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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BorrowService {


    private final BorrowRecordRepository borrowRecordRepository ;
    private final BorrowRecordMapper borrowRecordMapper;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;


    @Transactional
    public BorrowRecordResponse  borrow (BorrowBookRequest request){
        Member member=memberRepository.findById(request.getMemberId()).orElseThrow(()->new RuntimeException("member not found"));
        if (member.getStatus()== MemberStatus.BLOCKED){
            throw new RuntimeException("member is blocked");
        }
        Book book = bookRepository.findById(request.getBookId()).orElseThrow(()->new RuntimeException("Book not found"));
        if(book.getAvailableCopies()<=0){
            throw new RuntimeException("No copies available");

        }

        BorrowRecord borrowRecord=borrowRecordMapper.toEntity(request);
        borrowRecord.setMember(member);
        borrowRecord.setBook(book);
        book.setAvailableCopies(book.getAvailableCopies()-1);
        borrowRecordRepository.save(borrowRecord);
        return borrowRecordMapper.toResponse(borrowRecord);
    }

    @Transactional
    public void returnBook( ReturnBorrowBookRequest request) {

        BorrowRecord record = borrowRecordRepository.findById(request.getBorrowRecordId())
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        if (record.getStatus() != BorrowStatus.BORROWED) {
            throw new RuntimeException("Book already returned");
        }

        Book book = record.getBook();

        // update borrow record
        record.setStatus(BorrowStatus.RETURNED);
        record.setReturnDate(LocalDateTime.now());

        // update book inventory
        book.setAvailableCopies(book.getAvailableCopies() + 1);
    }

    public Page<BorrowRecordResponse> getBorrowListing (int page, int size){

        Pageable pageable= PageRequest.of(page,size, Sort.by("borrowDate").descending());

        return borrowRecordRepository.findByStatus(BorrowStatus.BORROWED,pageable).map(borrowRecordMapper::toResponse);
    }

    public List<BorrowRecordResponse> borrowHistory (Long id){
        Member member = memberRepository.findById(id).orElseThrow(()->new RuntimeException("member not found"));

         return borrowRecordRepository.findByMember(member).stream().map(borrowRecordMapper::toResponse).toList();
    }



}
