package org.example.SpringApiTesting.Controller;

import jakarta.validation.Valid;
import org.example.SpringApiTesting.DTO.*;
import org.example.SpringApiTesting.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/admin/login")
    public ResponseEntity<LoginResponse> adminLogin(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request, "ADMIN"));
    }

    @PostMapping("/librarian/login")
    public ResponseEntity<LoginResponse> librarianLogin(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request, "LIBRARIAN"));
    }

    @PostMapping("/member/login")
    public ResponseEntity<LoginResponse> memberLogin(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request, "MEMBER"));
    }

    @PostMapping("/member/register")
    public ResponseEntity<LoginResponse> memberRegister(
            @Valid @RequestBody MemberRegistrationRequest request) {
        return ResponseEntity.ok(authService.registerMember(request));
    }
}
