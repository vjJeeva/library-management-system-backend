package com.jeeva.Smart.Library.Management.dto.borrow.Response;
import com.jeeva.Smart.Library.Management.enums.BorrowStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BorrowRecordResponse {

    private Long id;

    private Long memberId;

    private Long bookId;

    private LocalDateTime borrowDate;

    private LocalDateTime returnDate;

    private BorrowStatus status;
}
