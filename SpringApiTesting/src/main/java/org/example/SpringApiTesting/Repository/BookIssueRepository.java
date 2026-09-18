package org.example.SpringApiTesting.Repository;

import org.example.SpringApiTesting.Entity.BookIssue;
import org.example.SpringApiTesting.Enum.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookIssueRepository extends JpaRepository<BookIssue, Long> {
    List<BookIssue> findByMemberEmail(String email);
    List<BookIssue> findByStatus(IssueStatus status);
}
