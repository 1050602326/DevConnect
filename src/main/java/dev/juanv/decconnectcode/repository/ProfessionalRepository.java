package dev.juanv.decconnectcode.repository;

import dev.juanv.decconnectcode.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessionalRepository
        extends JpaRepository<Professional, Long> {

    Optional<Professional> findByEmail(String email);

    boolean existsByEmail(String email);
}