package org.example.SpringApiTesting.Repository;

import org.example.SpringApiTesting.Entity.Fine;
import org.example.SpringApiTesting.Enum.FineStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FineRepository extends JpaRepository<Fine, Long> {
    List<Fine> findByMemberEmail(String email);
    List<Fine> findByStatus(FineStatus status);
}
