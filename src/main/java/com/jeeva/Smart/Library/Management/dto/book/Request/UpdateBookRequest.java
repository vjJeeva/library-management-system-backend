package com.jeeva.Smart.Library.Management.dto.book.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateBookRequest {

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Author name cannot be blank")
    private String author;

    @NotBlank(message = "Must give category")
    private String category;

}
