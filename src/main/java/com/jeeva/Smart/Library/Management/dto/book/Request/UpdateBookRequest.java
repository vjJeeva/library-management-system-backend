package com.jeeva.Smart.Library.Management.dto.book.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBookRequest {

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Author name cannot be blank")
    private String author;

    @NotBlank(message = "Must give category")
    private String category;

}
