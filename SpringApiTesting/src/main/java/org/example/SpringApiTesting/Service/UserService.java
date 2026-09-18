package org.example.SpringApiTesting.Service;

import org.example.SpringApiTesting.DTO.*;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    void deleteUser(Long id);
}
