
package dev.juanv.decconnectcode.service;

import dev.juanv.decconnectcode.dto.EducationRequest;
import dev.juanv.decconnectcode.model.Education;
import dev.juanv.decconnectcode.model.ProfessionalProfile;
import dev.juanv.decconnectcode.repository.EducationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EducationService {

    private final EducationRepository educationRepository;
    private final ProfileService profileService;

    public EducationService(
            EducationRepository educationRepository,
            ProfileService profileService) {
        this.educationRepository = educationRepository;
        this.profileService = profileService;
    }

    @Transactional
    public Education addEducation(Long professionalId, EducationRequest request) {
        ProfessionalProfile profile = profileService.findProfile(professionalId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Primero debes completar tu perfil profesional"));

        Education education = new Education();
        education.setProfile(profile);
        education.setInstitution(request.getInstitution().trim());
        education.setProgram(request.getProgram().trim());

        return educationRepository.save(education);
    }

    @Transactional(readOnly = true)
    public List<Education> findByProfessionalId(Long professionalId) {
        return profileService.findProfile(professionalId)
                .map(profile -> educationRepository.findByProfileId(profile.getId()))
                .orElseGet(List::of);
    }
    @Transactional
    public void deleteEducation(Long professionalId, Long educationId) {

        ProfessionalProfile profile = profileService.findProfile(professionalId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Perfil profesional no encontrado"));

        Education education = educationRepository.findById(educationId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Estudio no encontrado"));

        if (!education.getProfile().getId().equals(profile.getId())) {
            throw new IllegalArgumentException(
                    "No tienes permiso para eliminar este estudio"
            );
        }

        educationRepository.delete(education);
    }
    @Transactional
    public void updateEducation(
            Long professionalId,
            Long educationId,
            EducationRequest request
    ) {

        ProfessionalProfile profile = profileService.findProfile(professionalId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Perfil profesional no encontrado"));

        Education education = educationRepository.findById(educationId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Estudio no encontrado"));

        if (!education.getProfile().getId().equals(profile.getId())) {
            throw new IllegalArgumentException(
                    "No tienes permiso para editar este estudio"
            );
        }

        education.setInstitution(request.getInstitution());
        education.setProgram(request.getProgram());

        educationRepository.save(education);
    }
}
