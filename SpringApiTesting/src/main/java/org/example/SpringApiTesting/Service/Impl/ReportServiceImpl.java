package org.example.SpringApiTesting.Service.Impl;

import org.example.SpringApiTesting.Entity.Fine;
import org.example.SpringApiTesting.Enum.FineStatus;
import org.example.SpringApiTesting.Repository.*;
import org.example.SpringApiTesting.Service.ReportService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookIssueRepository issueRepository;
    private final FineRepository fineRepository;

    public ReportServiceImpl(
            UserRepository userRepository,
            BookRepository bookRepository,
            BookIssueRepository issueRepository,
            FineRepository fineRepository
    ) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.issueRepository = issueRepository;
        this.fineRepository = fineRepository;
    }

    @Override
    public Map<String, Object> getSummary() {
        BigDecimal unpaid = fineRepository.findByStatus(FineStatus.UNPAID)
                .stream()
                .map(Fine::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal paid = fineRepository.findByStatus(FineStatus.PAID)
                .stream()
                .map(Fine::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> result = new HashMap<>();
        result.put("totalUsers", userRepository.count());
        result.put("totalBooks", bookRepository.count());
        result.put("totalIssues", issueRepository.count());
        result.put("totalFines", fineRepository.count());
        result.put("unpaidFineAmount", unpaid);
        result.put("paidFineAmount", paid);
        return result;
    }
}
