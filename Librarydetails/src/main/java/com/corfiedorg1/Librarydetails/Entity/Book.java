package com.corfiedorg1.Librarydetails.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Books")
public class Book {
    @Id
    @Column(name="Book_Id" ,nullable=false)
    private Long Book_Id;
    @Column(name="title",nullable = false)
    private String title;
    @Column(name="author",nullable = false)
    private String author;
    @Column(name="genre",nullable = false)
    private String genre;
    @Column(name = "is_borrowed")
    private boolean isBorrowed;
    private LocalDate borrowedDate;
    @Column(name = "active")
    private boolean active;
    public void setIsBorrowed(boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }
}
