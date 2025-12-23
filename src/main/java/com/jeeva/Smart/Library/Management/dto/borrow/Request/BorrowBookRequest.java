package com.jeeva.Smart.Library.Management.dto.borrow.Request;
import com.jeeva.Smart.Library.Management.enums.BorrowStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class BorrowBookRequest {
    @NotNull(message = "Book id must be provided")
    private Long bookId;

    @NotNull(message = "Member id must be provided")
    private Long memberId;
}
