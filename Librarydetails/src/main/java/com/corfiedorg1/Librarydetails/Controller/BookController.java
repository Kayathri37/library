package com.corfiedorg1.Librarydetails.Controller;

import com.corfiedorg1.Librarydetails.DTO.BookRequestDto;
import com.corfiedorg1.Librarydetails.DTO.BookResponseDto;
import com.corfiedorg1.Librarydetails.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<BookResponseDto> addBook(@RequestBody BookRequestDto requestDto) {
        BookResponseDto response = bookService.addBook(requestDto);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Long id) {
        BookResponseDto response = bookService.getBookById(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PutMapping("/borrow/{id}")
    public ResponseEntity<BookResponseDto> borrowBook(@PathVariable Long id) {
        BookResponseDto response = bookService.borrowBook(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PutMapping("/return/{id}")
    public ResponseEntity<BookResponseDto> returnBook(@PathVariable Long id) {
        BookResponseDto response = bookService.returnBook(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/bookList")
    public ResponseEntity<List<BookResponseDto>> listAvailableBooks() {
        List<BookResponseDto> response = bookService.listAvailableBooks();
        return ResponseEntity.ok(response);
    }
}
