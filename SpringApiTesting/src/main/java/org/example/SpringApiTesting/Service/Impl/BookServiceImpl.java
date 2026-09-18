package org.example.SpringApiTesting.Service.Impl;

import org.example.SpringApiTesting.DTO.*;
import org.example.SpringApiTesting.Entity.Book;
import org.example.SpringApiTesting.Enum.BookStatus;
import org.example.SpringApiTesting.Exception.ResourceNotFoundException;
import org.example.SpringApiTesting.Repository.BookRepository;
import org.example.SpringApiTesting.Service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public List<BookResponse> searchBooks(String keyword) {
        String k = keyword == null ? "" : keyword.trim();
        if (k.isBlank()) return getAllBooks();

        return bookRepository
                .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrCategoryContainingIgnoreCase(k, k, k)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public BookResponse getBookById(Long id) {
        return toResponse(findBook(id));
    }

    @Override
    public BookResponse createBook(BookRequest request) {
        if (bookRepository.findByIsbn(request.isbn()).isPresent()) {
            throw new IllegalArgumentException("ISBN already exists");
        }

        Book book = new Book();
        book.setIsbn(request.isbn());
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setCategory(request.category());
        book.setTotalQuantity(request.totalQuantity());
        book.setAvailableQuantity(request.totalQuantity());
        book.setStatus(statusFor(request.totalQuantity()));

        return toResponse(bookRepository.save(book));
    }

    @Override
    public BookResponse updateBook(Long id, BookRequest request) {
        Book book = findBook(id);

        bookRepository.findByIsbn(request.isbn()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new IllegalArgumentException("ISBN already belongs to another book");
            }
        });

        int oldTotal = book.getTotalQuantity();
        int oldAvailable = book.getAvailableQuantity();
        int newTotal = request.totalQuantity();
        int borrowed = oldTotal - oldAvailable;

        if (newTotal < borrowed) {
            throw new IllegalArgumentException(
                    "Total quantity cannot be less than currently issued copies (" + borrowed + ")"
            );
        }

        book.setIsbn(request.isbn());
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setCategory(request.category());
        book.setTotalQuantity(newTotal);
        book.setAvailableQuantity(newTotal - borrowed);
        book.setStatus(statusFor(book.getAvailableQuantity()));

        return toResponse(bookRepository.save(book));
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.delete(findBook(id));
    }

    private Book findBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    private BookStatus statusFor(int available) {
        return available > 0 ? BookStatus.AVAILABLE : BookStatus.OUT_OF_STOCK;
    }

    private BookResponse toResponse(Book b) {
        return new BookResponse(
                b.getId(),
                b.getIsbn(),
                b.getTitle(),
                b.getAuthor(),
                b.getCategory(),
                b.getTotalQuantity(),
                b.getAvailableQuantity(),
                b.getStatus().name()
        );
    }
}
