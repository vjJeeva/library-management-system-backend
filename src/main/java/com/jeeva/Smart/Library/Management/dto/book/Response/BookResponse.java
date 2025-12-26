package com.jeeva.Smart.Library.Management.dto.book.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {

    private Long id;

    private String isbn;

    private String title;

    private String author;

    private String category;

    private Integer totalCopies;

    public Integer borrowedCopies;

    private Integer availableCopies;
}
