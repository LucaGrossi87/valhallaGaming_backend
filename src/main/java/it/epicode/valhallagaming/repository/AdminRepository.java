package it.epicode.valhallagaming.repository;

import it.epicode.valhallagaming.entity.Admin;
import it.epicode.valhallagaming.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByLoggedin (boolean loggedin);

    Optional<Admin> findByUserName (String username);

    List<Admin> findByRole(Role role);
}
