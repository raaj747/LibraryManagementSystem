package org.example.SpringApiTesting.Repository;

import org.example.SpringApiTesting.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByMemberEmail(String email);
}
