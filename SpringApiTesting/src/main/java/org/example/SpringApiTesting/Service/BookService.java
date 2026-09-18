package org.example.SpringApiTesting.Service;

import org.example.SpringApiTesting.DTO.*;

import java.util.List;

public interface BookService {
    List<BookResponse> getAllBooks();
    List<BookResponse> searchBooks(String keyword);
    BookResponse getBookById(Long id);
    BookResponse createBook(BookRequest request);
    BookResponse updateBook(Long id, BookRequest request);
    void deleteBook(Long id);
}
