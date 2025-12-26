package com.jeeva.Smart.Library.Management.dto.borrow.Response;
import com.jeeva.Smart.Library.Management.enums.BorrowStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowRecordResponse {

    private Long id;

    private Long memberId;

    private Long bookId;

    private LocalDateTime borrowDate;

    private LocalDateTime returnDate;

    private BorrowStatus status;
}
