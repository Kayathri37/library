package com.corfiedorg1.Librarydetails.Repo;

import com.corfiedorg1.Librarydetails.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsById(Long bookId);
}
