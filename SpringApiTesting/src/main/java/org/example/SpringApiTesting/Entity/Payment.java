package org.example.SpringApiTesting.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "fine_id", nullable = false)
    private Fine fine;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private User member;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 30)
    private String paymentMethod;

    @Column(nullable = false)
    private LocalDateTime paymentDate;

    public Payment() {}

    public Long getId() { return id; }
    public Fine getFine() { return fine; }
    public User getMember() { return member; }
    public BigDecimal getAmount() { return amount; }
    public String getPaymentMethod() { return paymentMethod; }
    public LocalDateTime getPaymentDate() { return paymentDate; }

    public void setId(Long id) { this.id = id; }
    public void setFine(Fine fine) { this.fine = fine; }
    public void setMember(User member) { this.member = member; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
}
