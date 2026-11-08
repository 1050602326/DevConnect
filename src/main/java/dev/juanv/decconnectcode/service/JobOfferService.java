
package dev.juanv.decconnectcode.service;

import dev.juanv.decconnectcode.model.Company;
import dev.juanv.decconnectcode.model.JobOffer;
import dev.juanv.decconnectcode.repository.CompanyRepository;
import dev.juanv.decconnectcode.repository.JobOfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobOfferService {

    private final JobOfferRepository jobOfferRepository;
    private final CompanyRepository companyRepository;

    public JobOfferService(JobOfferRepository jobOfferRepository,
                           CompanyRepository companyRepository) {
        this.jobOfferRepository = jobOfferRepository;
        this.companyRepository = companyRepository;
    }

    public JobOffer createOffer(JobOffer offer, Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada"));

        offer.setCompany(company);
        offer.setActive(true);
        return jobOfferRepository.save(offer);
    }

    public List<JobOffer> getCompanyOffers(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada"));

        return jobOfferRepository.findByCompany(company);
    }

    public List<JobOffer> getActiveOffers() {
        return jobOfferRepository.findByActiveTrue();
    }

    public JobOffer getOffer(Long offerId) {
        return jobOfferRepository.findById(offerId)
                .orElseThrow(() -> new IllegalArgumentException("Oferta no encontrada"));
    }

    public JobOffer updateOffer(Long offerId, Long companyId, JobOffer updated) {
        JobOffer offer = getOwnedOffer(offerId, companyId);

        offer.setTitle(updated.getTitle());
        offer.setDescription(updated.getDescription());
        offer.setRequirements(updated.getRequirements());
        offer.setLocation(updated.getLocation());
        offer.setSalary(updated.getSalary());
        offer.setModality(updated.getModality());
        offer.setEmploymentType(updated.getEmploymentType());

        return jobOfferRepository.save(offer);
    }

    public void changeStatus(Long offerId, Long companyId, boolean active) {
        JobOffer offer = getOwnedOffer(offerId, companyId);
        offer.setActive(active);
        jobOfferRepository.save(offer);
    }

    public JobOffer getOwnedOffer(Long offerId, Long companyId) {
        JobOffer offer = getOffer(offerId);

        if (!offer.getCompany().getId().equals(companyId)) {
            throw new IllegalArgumentException("No tienes permiso para modificar esta oferta");
        }

        return offer;
    }



    public JobOffer getActiveOffer(Long offerId) {
        JobOffer offer = jobOfferRepository.findById(offerId)
                .orElseThrow(() -> new IllegalArgumentException("Oferta no encontrada"));

        if (!offer.isActive()) {
            throw new IllegalArgumentException("Esta oferta ya no está disponible");
        }

        return offer;
    }




}
