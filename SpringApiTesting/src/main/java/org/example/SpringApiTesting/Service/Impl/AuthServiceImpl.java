package org.example.SpringApiTesting.Service.Impl;

import org.example.SpringApiTesting.Config.JwtTokenProvider;
import org.example.SpringApiTesting.DTO.*;
import org.example.SpringApiTesting.Entity.Role;
import org.example.SpringApiTesting.Entity.User;
import org.example.SpringApiTesting.Enum.RoleName;
import org.example.SpringApiTesting.Exception.ResourceNotFoundException;
import org.example.SpringApiTesting.Exception.UnauthorizedException;
import org.example.SpringApiTesting.Repository.RoleRepository;
import org.example.SpringApiTesting.Repository.UserRepository;
import org.example.SpringApiTesting.Service.AuthService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public LoginResponse login(LoginRequest request, String expectedRole) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String actualRole = user.getRole().getName().name();

        if (!actualRole.equals(expectedRole)) {
            throw new UnauthorizedException(
                    "This account is not a " + expectedRole.toLowerCase() + " account"
            );
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                java.util.List.of(
                        new org.springframework.security.core.authority.SimpleGrantedAuthority(
                                "ROLE_" + actualRole
                        )
                )
        );

        String token = jwtTokenProvider.generateToken(userDetails, actualRole);

        return new LoginResponse(
                token,
                "Bearer",
                user.getId(),
                user.getName(),
                user.getEmail(),
                actualRole
        );
    }

    @Override
    public LoginResponse registerMember(MemberRegistrationRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Role memberRole = roleRepository.findByName(RoleName.MEMBER)
                .orElseThrow(() -> new ResourceNotFoundException("MEMBER role not found"));

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(memberRole);
        user.setEnabled(true);

        User saved = userRepository.save(user);

        return new LoginResponse(
                null,
                null,
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getRole().getName().name()
        );
    }
}
