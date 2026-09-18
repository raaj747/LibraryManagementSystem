package org.example.SpringApiTesting.Controller;

import org.example.SpringApiTesting.Service.IssueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/librarian")
public class LibrarianController {

    private final IssueService issueService;

    public LibrarianController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/issues-summary")
    public ResponseEntity<?> getAllIssues() {
        return ResponseEntity.ok(issueService.getAllIssues());
    }
}
