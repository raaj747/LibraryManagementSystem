package org.example.SpringApiTesting.Entity;

import jakarta.persistence.*;
import org.example.SpringApiTesting.Enum.FineStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fines")
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "issue_id", nullable = false, unique = true)
    private BookIssue issue;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private User member;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FineStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime paidAt;

    public Fine() {}

    public Long getId() { return id; }
    public BookIssue getIssue() { return issue; }
    public User getMember() { return member; }
    public BigDecimal getAmount() { return amount; }
    public FineStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getPaidAt() { return paidAt; }

    public void setId(Long id) { this.id = id; }
    public void setIssue(BookIssue issue) { this.issue = issue; }
    public void setMember(User member) { this.member = member; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setStatus(FineStatus status) { this.status = status; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }
}
