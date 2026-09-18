package org.example.SpringApiTesting.Entity;

import jakarta.persistence.*;
import org.example.SpringApiTesting.Enum.IssueStatus;

import java.time.LocalDate;

@Entity
@Table(name = "book_issues")
public class BookIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private User member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "issued_by")
    private User issuedBy;

    @Column(nullable = false)
    private LocalDate issueDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private IssueStatus status;

    public BookIssue() {}

    public Long getId() { return id; }
    public Book getBook() { return book; }
    public User getMember() { return member; }
    public User getIssuedBy() { return issuedBy; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public IssueStatus getStatus() { return status; }

    public void setId(Long id) { this.id = id; }
    public void setBook(Book book) { this.book = book; }
    public void setMember(User member) { this.member = member; }
    public void setIssuedBy(User issuedBy) { this.issuedBy = issuedBy; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public void setStatus(IssueStatus status) { this.status = status; }
}
