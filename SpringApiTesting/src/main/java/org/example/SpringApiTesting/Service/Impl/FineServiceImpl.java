package org.example.SpringApiTesting.Service.Impl;

import org.example.SpringApiTesting.DTO.*;
import org.example.SpringApiTesting.Entity.Fine;
import org.example.SpringApiTesting.Entity.Payment;
import org.example.SpringApiTesting.Entity.User;
import org.example.SpringApiTesting.Enum.FineStatus;
import org.example.SpringApiTesting.Exception.ResourceNotFoundException;
import org.example.SpringApiTesting.Exception.UnauthorizedException;
import org.example.SpringApiTesting.Repository.FineRepository;
import org.example.SpringApiTesting.Repository.PaymentRepository;
import org.example.SpringApiTesting.Repository.UserRepository;
import org.example.SpringApiTesting.Service.FineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FineServiceImpl implements FineService {

    private final FineRepository fineRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    public FineServiceImpl(
            FineRepository fineRepository,
            PaymentRepository paymentRepository,
            UserRepository userRepository
    ) {
        this.fineRepository = fineRepository;
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<FineResponse> getMemberFines(String email) {
        return fineRepository.findByMemberEmail(email).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<FineResponse> getAllFines() {
        return fineRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public FineResponse payFine(PaymentRequest request, String memberEmail) {
        User member = userRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        Fine fine = fineRepository.findById(request.fineId())
                .orElseThrow(() -> new ResourceNotFoundException("Fine not found"));

        if (!fine.getMember().getId().equals(member.getId())) {
            throw new UnauthorizedException("You can pay only your own fine");
        }

        if (fine.getStatus() != FineStatus.UNPAID) {
            throw new IllegalArgumentException("This fine is not payable");
        }

        Payment payment = new Payment();
        payment.setFine(fine);
        payment.setMember(member);
        payment.setAmount(fine.getAmount());
        payment.setPaymentMethod(request.paymentMethod());
        payment.setPaymentDate(LocalDateTime.now());

        paymentRepository.save(payment);

        fine.setStatus(FineStatus.PAID);
        fine.setPaidAt(LocalDateTime.now());

        return toResponse(fineRepository.save(fine));
    }

    @Override
    public FineResponse waiveFine(Long fineId) {
        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() -> new ResourceNotFoundException("Fine not found"));

        if (fine.getStatus() == FineStatus.PAID) {
            throw new IllegalArgumentException("Paid fine cannot be waived");
        }

        fine.setStatus(FineStatus.WAIVED);
        return toResponse(fineRepository.save(fine));
    }

    private FineResponse toResponse(Fine f) {
        return new FineResponse(
                f.getId(),
                f.getIssue().getId(),
                f.getMember().getId(),
                f.getMember().getName(),
                f.getAmount(),
                f.getStatus().name(),
                f.getCreatedAt(),
                f.getPaidAt()
        );
    }
}
