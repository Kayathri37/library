package com.corfiedorg1.Librarydetails.DTO;

import com.corfiedorg1.Librarydetails.Entity.Book;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookRequestDto {
    private Long Book_Id; // Match the Book entity field
    private String title;
    private String author;
    private String genre;
    private boolean isBorrowed;
    private LocalDate borrowedDate;

    public Book toEntity() {
        Book book = new Book();
        book.setBook_Id(this.Book_Id);
        book.setTitle(this.title);
        book.setAuthor(this.author);
        book.setGenre(this.genre);
        book.setIsBorrowed(this.isBorrowed);
        book.setBorrowedDate(this.borrowedDate);
        book.setActive(true);
        return book;
    }
}
