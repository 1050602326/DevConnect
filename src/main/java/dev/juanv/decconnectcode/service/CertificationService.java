
package dev.juanv.decconnectcode.service;

import dev.juanv.decconnectcode.dto.CertificationRequest;
import dev.juanv.decconnectcode.model.Certification;
import dev.juanv.decconnectcode.model.ProfessionalProfile;
import dev.juanv.decconnectcode.repository.CertificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CertificationService {

    private final CertificationRepository certificationRepository;
    private final ProfileService profileService;

    public CertificationService(
            CertificationRepository certificationRepository,
            ProfileService profileService) {
        this.certificationRepository = certificationRepository;
        this.profileService = profileService;
    }

    @Transactional
    public Certification addCertification(
            Long professionalId,
            CertificationRequest request) {

        ProfessionalProfile profile = profileService.findProfile(professionalId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Primero debes completar tu perfil profesional"));

        Certification certification = new Certification();
        certification.setProfile(profile);
        certification.setName(request.getName().trim());
        certification.setInstitution(request.getInstitution().trim());

        return certificationRepository.save(certification);
    }

    @Transactional(readOnly = true)
    public List<Certification> findByProfessionalId(Long professionalId) {
        return profileService.findProfile(professionalId)
                .map(profile -> certificationRepository.findByProfileId(profile.getId()))
                .orElseGet(List::of);
    }
}
