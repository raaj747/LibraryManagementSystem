package org.example.SpringApiTesting.Controller;

import org.example.SpringApiTesting.Repository.UserRepository;
import org.example.SpringApiTesting.DTO.UserResponse;
import org.example.SpringApiTesting.Exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final UserRepository userRepository;

    public MemberController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> profile(Authentication authentication) {
        var user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        return ResponseEntity.ok(new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().getName().name(),
                user.isEnabled()
        ));
    }
}
