package com.jeeva.Smart.Library.Management.repository;

import com.jeeva.Smart.Library.Management.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

}
