package org.example.SpringApiTesting.Service.Impl;

import org.example.SpringApiTesting.DTO.*;
import org.example.SpringApiTesting.Entity.*;
import org.example.SpringApiTesting.Enum.*;
import org.example.SpringApiTesting.Exception.*;
import org.example.SpringApiTesting.Repository.*;
import org.example.SpringApiTesting.Service.IssueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class IssueServiceImpl implements IssueService {

    private static final BigDecimal FINE_PER_DAY = BigDecimal.TEN;

    private final BookIssueRepository issueRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final FineRepository fineRepository;

    public IssueServiceImpl(
            BookIssueRepository issueRepository,
            BookRepository bookRepository,
            UserRepository userRepository,
            FineRepository fineRepository
    ) {
        this.issueRepository = issueRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.fineRepository = fineRepository;
    }

    @Override
    @Transactional
    public IssueResponse issueForMember(IssueRequest request, String memberEmail) {
        User member = findUserByEmail(memberEmail);
        return createIssue(request.bookId(), member, null, request.dueDate());
    }

    @Override
    @Transactional
    public IssueResponse issueForLibrarian(IssueRequest request, String librarianEmail) {
        User librarian = findUserByEmail(librarianEmail);

        if (request.memberId() == null) {
            throw new IllegalArgumentException("memberId is required for librarian issue operation");
        }

        User member = userRepository.findById(request.memberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        if (member.getRole().getName() != RoleName.MEMBER) {
            throw new IllegalArgumentException("Selected user is not a member");
        }

        return createIssue(request.bookId(), member, librarian, request.dueDate());
    }

    private IssueResponse createIssue(
            Long bookId,
            User member,
            User issuedBy,
            LocalDate dueDate
    ) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        if (book.getAvailableQuantity() <= 0) {
            throw new BookNotAvailableException("Book is currently out of stock");
        }

        BookIssue issue = new BookIssue();
        issue.setBook(book);
        issue.setMember(member);
        issue.setIssuedBy(issuedBy);
        issue.setIssueDate(LocalDate.now());
        issue.setDueDate(dueDate);
        issue.setStatus(IssueStatus.ISSUED);

        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        book.setStatus(book.getAvailableQuantity() > 0
                ? BookStatus.AVAILABLE
                : BookStatus.OUT_OF_STOCK);

        bookRepository.save(book);
        return toResponse(issueRepository.save(issue));
    }

    @Override
    @Transactional
    public IssueResponse returnBook(Long issueId, String requesterEmail, boolean librarian) {
        BookIssue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Issue record not found"));

        if (issue.getStatus() == IssueStatus.RETURNED) {
            throw new IllegalArgumentException("This book has already been returned");
        }

        if (!librarian && !issue.getMember().getEmail().equals(requesterEmail)) {
            throw new UnauthorizedException("You can return only your own issued book");
        }

        LocalDate returnDate = LocalDate.now();
        issue.setReturnDate(returnDate);
        issue.setStatus(IssueStatus.RETURNED);

        Book book = issue.getBook();
        book.setAvailableQuantity(book.getAvailableQuantity() + 1);
        book.setStatus(BookStatus.AVAILABLE);
        bookRepository.save(book);

        if (returnDate.isAfter(issue.getDueDate())) {
            long lateDays = ChronoUnit.DAYS.between(issue.getDueDate(), returnDate);

            Fine fine = new Fine();
            fine.setIssue(issue);
            fine.setMember(issue.getMember());
            fine.setAmount(FINE_PER_DAY.multiply(BigDecimal.valueOf(lateDays)));
            fine.setStatus(FineStatus.UNPAID);
            fine.setCreatedAt(java.time.LocalDateTime.now());
            fineRepository.save(fine);
        }

        return toResponse(issueRepository.save(issue));
    }

    @Override
    public List<IssueResponse> getMemberIssues(String memberEmail) {
        return issueRepository.findByMemberEmail(memberEmail)
                .stream().map(this::toResponse).toList();
    }

    @Override
    public List<IssueResponse> getAllIssues() {
        return issueRepository.findAll().stream().map(this::toResponse).toList();
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private IssueResponse toResponse(BookIssue i) {
        return new IssueResponse(
                i.getId(),
                i.getBook().getId(),
                i.getBook().getTitle(),
                i.getMember().getId(),
                i.getMember().getName(),
                i.getIssueDate(),
                i.getDueDate(),
                i.getReturnDate(),
                i.getStatus().name()
        );
    }
}
