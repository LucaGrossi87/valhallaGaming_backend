package it.epicode.valhallagaming.repository;

import it.epicode.valhallagaming.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
}
