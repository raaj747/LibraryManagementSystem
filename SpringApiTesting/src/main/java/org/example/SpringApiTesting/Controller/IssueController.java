package org.example.SpringApiTesting.Controller;

import jakarta.validation.Valid;
import org.example.SpringApiTesting.DTO.*;
import org.example.SpringApiTesting.Service.IssueService;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping("/member/issues")
    public ResponseEntity<IssueResponse> memberIssue(
            @Valid @RequestBody IssueRequest request,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(issueService.issueForMember(request, authentication.getName()));
    }

    @GetMapping("/member/issues")
    public ResponseEntity<List<IssueResponse>> memberIssues(
            Authentication authentication) {
        return ResponseEntity.ok(issueService.getMemberIssues(authentication.getName()));
    }

    @PostMapping("/member/issues/{id}/return")
    public ResponseEntity<IssueResponse> memberReturn(
            @PathVariable Long id,
            Authentication authentication) {
        return ResponseEntity.ok(
                issueService.returnBook(id, authentication.getName(), false)
        );
    }

    @PostMapping("/librarian/issues")
    public ResponseEntity<IssueResponse> librarianIssue(
            @Valid @RequestBody IssueRequest request,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(issueService.issueForLibrarian(request, authentication.getName()));
    }

    @PostMapping("/librarian/issues/{id}/return")
    public ResponseEntity<IssueResponse> librarianReturn(@PathVariable Long id) {
        return ResponseEntity.ok(issueService.returnBook(id, null, true));
    }

    @GetMapping("/librarian/issues")
    public ResponseEntity<List<IssueResponse>> librarianIssues() {
        return ResponseEntity.ok(issueService.getAllIssues());
    }
}
