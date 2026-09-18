package org.example.SpringApiTesting.Service;

import org.example.SpringApiTesting.DTO.*;

import java.util.List;

public interface FineService {
    List<FineResponse> getMemberFines(String email);
    List<FineResponse> getAllFines();
    FineResponse payFine(PaymentRequest request, String memberEmail);
    FineResponse waiveFine(Long fineId);
}
