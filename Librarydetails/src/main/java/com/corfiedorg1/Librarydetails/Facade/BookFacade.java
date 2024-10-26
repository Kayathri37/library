package com.corfiedorg1.Librarydetails.Facade;

import com.corfiedorg1.Librarydetails.DTO.BookRequestDto;
import com.corfiedorg1.Librarydetails.DTO.BookResponseDto;
import com.corfiedorg1.Librarydetails.Entity.Book;
import com.corfiedorg1.Librarydetails.Repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BookFacade {
    @Autowired
    private BookRepository bookRepository;
    public BookResponseDto addBook(BookRequestDto requestDto) {
        try {
            String validationMessage = validateBookRequest(requestDto);
            if (validationMessage != null) {
                return new BookResponseDto(null, null, null, null, false, validationMessage, false, HttpStatus.BAD_REQUEST);
            }

            Long bookId = requestDto.getBook_Id();
            Book existingBook = bookRepository.findById(bookId).orElse(null);

            if (existingBook != null) {
                return new BookResponseDto(existingBook.getBook_Id(), existingBook.getTitle(), existingBook.getAuthor(), existingBook.getGenre(),
                        existingBook.isBorrowed(), "Book ID already exists", true, HttpStatus.OK);
            }

            Book book = requestDto.toEntity();
            book.setIsBorrowed(false);
            bookRepository.save(book);
            return new BookResponseDto(book.getBook_Id(), book.getTitle(), book.getAuthor(), book.getGenre(),
                    false, "Book added successfully!", true, HttpStatus.CREATED);
        } catch (Exception e) {
            return new BookResponseDto(null, null, null, null, false, "An unexpected error occurred: " + e.getMessage(), false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


private String validateBookRequest(BookRequestDto requestDto) {
    if (requestDto.getBook_Id() == null) {
        return "Book ID cannot be null.";
    }
    if (requestDto.getTitle() == null || requestDto.getTitle().isEmpty()) {
        return "Title cannot be null or empty.";
    }
    if (requestDto.getAuthor() == null || requestDto.getAuthor().isEmpty()) {
        return "Author cannot be null or empty.";
    }
    if (requestDto.getGenre() == null || requestDto.getGenre().isEmpty()) {
        return "Genre cannot be null or empty.";
    }
    return null; // No validation errors
}

    public BookResponseDto borrowBook(Long id) {
        try {
            if (id == null || id <= 0) {
                return new BookResponseDto(null, null, null, null, false, "Invalid book ID provided.", false, HttpStatus.BAD_REQUEST);
            }
            Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
            if (book.isBorrowed()) {
                return new BookResponseDto(book.getBook_Id(), book.getTitle(), book.getAuthor(), book.getGenre(),
                        true, "Book is already borrowed.", true, HttpStatus.OK);
            }
            book.setIsBorrowed(true);
            book.setBorrowedDate(LocalDate.now());
            bookRepository.save(book); // Save the updated book entity
            return new BookResponseDto(book.getBook_Id(), book.getTitle(), book.getAuthor(), book.getGenre(),
                    true, "Book borrowed successfully!", true, HttpStatus.OK);
        } catch (Exception e) {
            return new BookResponseDto(null, null, null, null, false, "An error occurred: " + e.getMessage(), false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public BookResponseDto returnBook(Long id) {
        try {
            Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
            if (!book.isBorrowed()) {
                return new BookResponseDto(book.getBook_Id(), book.getTitle(), book.getAuthor(), book.getGenre(),
                        false, "Book is not borrowed", false, HttpStatus.CONFLICT);
            }
            book.setIsBorrowed(false);
            book.setBorrowedDate(null);
            bookRepository.save(book);
            return new BookResponseDto(book.getBook_Id(), book.getTitle(), book.getAuthor(), book.getGenre(),
                    false, "Book returned successfully!", true, HttpStatus.OK);
        } catch (Exception e) {
            return new BookResponseDto(null, null, null, null, false, "An error occurred: " + e.getMessage(), false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
