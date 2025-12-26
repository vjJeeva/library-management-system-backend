package com.jeeva.Smart.Library.Management.dto.book.Request;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookRequest {

    @NotBlank
    @Pattern(regexp ="^(\\d{10}|\\d{13})$", message = "isbn must be 10 or 13 Characters")
    private String isbn;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Author name cannot be blank")
    private String author;

    @NotBlank(message = "Must give category")
    private String category;

    @PositiveOrZero(message = "values must be 0 or above")
    private Integer totalCopies;
}
