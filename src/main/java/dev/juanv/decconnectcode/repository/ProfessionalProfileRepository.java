package dev.juanv.decconnectcode.repository;

import dev.juanv.decconnectcode.model.ProfessionalProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessionalProfileRepository
        extends JpaRepository<ProfessionalProfile, Long> {

    Optional<ProfessionalProfile>
    findByProfessionalId(Long professionalId);
}