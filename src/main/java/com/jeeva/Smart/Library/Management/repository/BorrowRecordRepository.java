package com.jeeva.Smart.Library.Management.repository;

import com.jeeva.Smart.Library.Management.enums.BorrowStatus;
import com.jeeva.Smart.Library.Management.model.BorrowRecord;
import com.jeeva.Smart.Library.Management.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord,Long> {

    Page <BorrowRecord> findByStatus(BorrowStatus status,Pageable pageable);

    List<BorrowRecord> findByMember (Member member);
}
