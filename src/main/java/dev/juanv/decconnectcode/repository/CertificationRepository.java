package dev.juanv.decconnectcode.repository;

import dev.juanv.decconnectcode.model.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificationRepository
        extends JpaRepository<Certification, Long> {

    List<Certification> findByProfileId(Long profileId);
}