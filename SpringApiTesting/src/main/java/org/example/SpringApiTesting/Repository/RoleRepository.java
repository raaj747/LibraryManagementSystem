package org.example.SpringApiTesting.Repository;

import org.example.SpringApiTesting.Entity.Role;
import org.example.SpringApiTesting.Enum.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
