package dev.juanv.decconnectcode.repository;

import dev.juanv.decconnectcode.model.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository
        extends JpaRepository<Education, Long> {

    List<Education> findByProfileId(Long profileId);
}