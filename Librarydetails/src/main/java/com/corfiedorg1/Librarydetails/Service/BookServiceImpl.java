package com.corfiedorg1.Librarydetails.Service;

import com.corfiedorg1.Librarydetails.DTO.BookRequestDto;
import com.corfiedorg1.Librarydetails.DTO.BookResponseDto;
import com.corfiedorg1.Librarydetails.Entity.Book;
import com.corfiedorg1.Librarydetails.Facade.BookFacade;
import com.corfiedorg1.Librarydetails.Repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookFacade bookFacade;

    @Override
    public BookResponseDto addBook(BookRequestDto requestDto) {
        return bookFacade.addBook(requestDto);
    }

    @Override
    public BookResponseDto getBookById(Long id) {
        try {
            if (id == null || id <= 0) {
                return new BookResponseDto(null, null, null, null, false, "Invalid book ID provided.", false, HttpStatus.BAD_REQUEST);
            }
            Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
            return new BookResponseDto(book.getBook_Id(), book.getTitle(), book.getAuthor(), book.getGenre(), book.isBorrowed(), "Book retrieved successfully!", true, HttpStatus.OK);
        } catch (Exception e) {
            return new BookResponseDto(null, null, null, null, false, "An error occurred: " + e.getMessage(), false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public BookResponseDto borrowBook(Long id) {
        return bookFacade.borrowBook(id);
    }

    @Override
    public BookResponseDto returnBook(Long id) {
        return bookFacade.returnBook(id);
    }

    @Override
    public List<BookResponseDto> listAvailableBooks() {
        List<Book> availableBooks = bookRepository.findAll().stream().filter(book -> !book.isBorrowed()).collect(Collectors.toList());
        return availableBooks.stream().map(book -> new BookResponseDto(book.getBook_Id(), book.getTitle(), book.getAuthor(), book.getGenre(),
                        false, "Available book: " + book.getTitle(), true, HttpStatus.OK))
                .collect(Collectors.toList());
    }
}
