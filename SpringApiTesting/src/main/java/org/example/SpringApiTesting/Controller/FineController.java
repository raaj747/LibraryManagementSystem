package org.example.SpringApiTesting.Controller;

import jakarta.validation.Valid;
import org.example.SpringApiTesting.DTO.*;
import org.example.SpringApiTesting.Service.FineService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FineController {

    private final FineService fineService;

    public FineController(FineService fineService) {
        this.fineService = fineService;
    }

    @GetMapping("/member/fines")
    public ResponseEntity<List<FineResponse>> memberFines(
            Authentication authentication) {
        return ResponseEntity.ok(fineService.getMemberFines(authentication.getName()));
    }

    @PostMapping("/member/fines/pay")
    public ResponseEntity<FineResponse> payFine(
            @Valid @RequestBody PaymentRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(
                fineService.payFine(request, authentication.getName())
        );
    }

    @GetMapping("/librarian/fines")
    public ResponseEntity<List<FineResponse>> allFines() {
        return ResponseEntity.ok(fineService.getAllFines());
    }

    @PostMapping("/librarian/fines/{id}/waive")
    public ResponseEntity<FineResponse> waiveFine(@PathVariable Long id) {
        return ResponseEntity.ok(fineService.waiveFine(id));
    }
}
