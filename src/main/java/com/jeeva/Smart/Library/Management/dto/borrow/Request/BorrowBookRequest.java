package com.jeeva.Smart.Library.Management.dto.borrow.Request;
import com.jeeva.Smart.Library.Management.enums.BorrowStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowBookRequest {
    @NotNull(message = "Book id must be provided")
    private Long bookId;

    @NotNull(message = "Member id must be provided")
    private Long memberId;
}
