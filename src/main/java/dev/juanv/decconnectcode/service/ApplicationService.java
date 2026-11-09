package dev.juanv.decconnectcode.service;

import dev.juanv.decconnectcode.model.Application;
import dev.juanv.decconnectcode.model.JobOffer;
import dev.juanv.decconnectcode.model.Professional;
import dev.juanv.decconnectcode.repository.ApplicationRepository;
import dev.juanv.decconnectcode.repository.JobOfferRepository;
import dev.juanv.decconnectcode.repository.ProfessionalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApplicationService {

    public static final List<String> STATUSES =
            List.of("PENDIENTE", "REVISADA", "ACEPTADA", "RECHAZADA");

    private final ApplicationRepository applicationRepository;
    private final JobOfferRepository jobOfferRepository;
    private final ProfessionalRepository professionalRepository;

    public ApplicationService(
            ApplicationRepository applicationRepository,
            JobOfferRepository jobOfferRepository,
            ProfessionalRepository professionalRepository) {
        this.applicationRepository = applicationRepository;
        this.jobOfferRepository = jobOfferRepository;
        this.professionalRepository = professionalRepository;
    }

    @Transactional
    public Application apply(Long professionalId, Long jobOfferId) {

        Professional professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado"));

        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));

        if (!jobOffer.isActive()) {
            throw new RuntimeException("Esta oferta ya no está disponible");
        }

        boolean alreadyApplied =
                applicationRepository.existsByProfessionalAndJobOffer(professional, jobOffer);

        if (alreadyApplied) {
            throw new RuntimeException("Ya te has postulado a esta oferta");
        }

        Application application = new Application();
        application.setProfessional(professional);
        application.setJobOffer(jobOffer);

        return applicationRepository.save(application);
    }

    @Transactional(readOnly = true)
    public List<Application> getProfessionalApplications(Long professionalId) {

        Professional professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado"));

        return applicationRepository.findByProfessionalOrderByAppliedAtDesc(professional);
    }

    @Transactional(readOnly = true)
    public List<Application> getCompanyApplications(Long companyId) {
        return applicationRepository.findByJobOffer_Company_IdOrderByAppliedAtDesc(companyId);
    }

    // ---------- Tarea 17: estado de la postulación ----------

    @Transactional
    public void updateStatus(Long companyId, Long applicationId, String status) {
        if (!STATUSES.contains(status)) {
            throw new RuntimeException("Estado no válido");
        }

        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada"));

        if (!app.getJobOffer().getCompany().getId().equals(companyId)) {
            throw new RuntimeException("No tienes permiso sobre esta postulación");
        }

        app.setStatus(status);
        applicationRepository.save(app);
    }

    // ---------- Para el botón "Postularme" ----------

    @Transactional(readOnly = true)
    public boolean hasApplied(Long professionalId, Long jobOfferId) {
        return applicationRepository.existsByProfessionalAndJobOffer(
                professionalRepository.getReferenceById(professionalId),
                jobOfferRepository.getReferenceById(jobOfferId));
    }
}