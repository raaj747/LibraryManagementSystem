package org.example.SpringApiTesting.Service;

import org.example.SpringApiTesting.DTO.*;

import java.util.List;

public interface IssueService {
    IssueResponse issueForMember(IssueRequest request, String memberEmail);
    IssueResponse issueForLibrarian(IssueRequest request, String librarianEmail);
    IssueResponse returnBook(Long issueId, String requesterEmail, boolean librarian);
    List<IssueResponse> getMemberIssues(String memberEmail);
    List<IssueResponse> getAllIssues();
}
