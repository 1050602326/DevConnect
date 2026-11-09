package dev.juanv.decconnectcode.repository;

import dev.juanv.decconnectcode.model.Application;
import dev.juanv.decconnectcode.model.JobOffer;
import dev.juanv.decconnectcode.model.Professional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByProfessionalAndJobOffer(
            Professional professional,
            JobOffer jobOffer
    );

    @EntityGraph(attributePaths = {"jobOffer", "jobOffer.company"})
    List<Application> findByProfessionalOrderByAppliedAtDesc(
            Professional professional
    );

    @EntityGraph(attributePaths = {"jobOffer", "professional"})
    List<Application> findByJobOffer_Company_IdOrderByAppliedAtDesc(
            Long companyId
    );
}