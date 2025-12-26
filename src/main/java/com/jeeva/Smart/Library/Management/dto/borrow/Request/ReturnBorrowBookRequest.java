package com.jeeva.Smart.Library.Management.dto.borrow.Request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnBorrowBookRequest {
    @NotNull(message = "BorrowId must be submitted")
    private Long borrowRecordId;

}
