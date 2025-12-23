package com.jeeva.Smart.Library.Management.dto.borrow.Request;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReturnBorrowBookRequest {
    @NotNull(message = "BorrowId must be submitted")
    private Long borrowRecordId;

}
