package org.example.SpringApiTesting.Config;

import org.example.SpringApiTesting.Entity.Role;
import org.example.SpringApiTesting.Entity.User;
import org.example.SpringApiTesting.Enum.RoleName;
import org.example.SpringApiTesting.Repository.RoleRepository;
import org.example.SpringApiTesting.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            for (RoleName roleName : RoleName.values()) {
                roleRepository.findByName(roleName)
                        .orElseGet(() -> roleRepository.save(new Role(roleName)));
            }

            createIfMissing(
                    userRepository, roleRepository, passwordEncoder,
                    "System Admin", "admin@example.com", "password", RoleName.ADMIN
            );

            createIfMissing(
                    userRepository, roleRepository, passwordEncoder,
                    "Main Librarian", "librarian@example.com", "password", RoleName.LIBRARIAN
            );

            createIfMissing(
                    userRepository, roleRepository, passwordEncoder,
                    "Demo Member", "member@example.com", "password", RoleName.MEMBER
            );
        };
    }

    private void createIfMissing(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            String name,
            String email,
            String rawPassword,
            RoleName roleName
    ) {
        if (userRepository.existsByEmail(email)) return;

        Role role = roleRepository.findByName(roleName).orElseThrow();

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(role);
        user.setEnabled(true);

        userRepository.save(user);
    }
}
