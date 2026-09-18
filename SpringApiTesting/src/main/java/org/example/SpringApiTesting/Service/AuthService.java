package org.example.SpringApiTesting.Service;

import org.example.SpringApiTesting.DTO.*;

public interface AuthService {
    LoginResponse login(LoginRequest request, String expectedRole);
    LoginResponse registerMember(MemberRegistrationRequest request);
}
