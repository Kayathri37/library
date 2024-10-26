package com.corfiedorg1.Librarydetails.Service;

import com.corfiedorg1.Librarydetails.DTO.BookRequestDto;
import com.corfiedorg1.Librarydetails.DTO.BookResponseDto;

import java.util.List;

public interface BookService {
    BookResponseDto addBook(BookRequestDto requestDto);
    BookResponseDto getBookById(Long id);
    BookResponseDto borrowBook(Long id);
    BookResponseDto returnBook(Long id);
    List<BookResponseDto> listAvailableBooks();
}
