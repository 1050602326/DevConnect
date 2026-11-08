package dev.juanv.decconnectcode.service;

import dev.juanv.decconnectcode.dto.SkillRequest;
import dev.juanv.decconnectcode.model.ProfessionalProfile;
import dev.juanv.decconnectcode.model.Skill;
import dev.juanv.decconnectcode.repository.SkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SkillService {

    private final SkillRepository skillRepository;
    private final ProfileService profileService;

    public SkillService(
            SkillRepository skillRepository,
            ProfileService profileService) {
        this.skillRepository = skillRepository;
        this.profileService = profileService;
    }

    @Transactional
    public Skill addSkill(Long professionalId, SkillRequest request) {
        ProfessionalProfile profile = profileService.findProfile(professionalId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Primero debes completar tu perfil profesional"));

        Skill skill = new Skill();
        skill.setProfile(profile);
        skill.setName(request.getName().trim());

        return skillRepository.save(skill);
    }

    @Transactional
    public Skill updateSkill(Long professionalId, Long skillId, SkillRequest request) {
        Skill skill = findOwnedSkill(professionalId, skillId);
        skill.setName(request.getName().trim());
        return skillRepository.save(skill);
    }

    @Transactional
    public void deleteSkill(Long professionalId, Long skillId) {
        Skill skill = findOwnedSkill(professionalId, skillId);
        skillRepository.delete(skill);
    }

    @Transactional(readOnly = true)
    public List<Skill> findByProfessionalId(Long professionalId) {
        return profileService.findProfile(professionalId)
                .map(profile -> skillRepository.findByProfileId(profile.getId()))
                .orElseGet(List::of);
    }

    /**
     * Busca la skill y verifica que pertenezca al perfil del profesional.
     * Lanza IllegalArgumentException si no existe o no es suya.
     */
    private Skill findOwnedSkill(Long professionalId, Long skillId) {
        ProfessionalProfile profile = profileService.findProfile(professionalId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Perfil profesional no encontrado"));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Skill no encontrada"));

        if (!skill.getProfile().getId().equals(profile.getId())) {
            throw new IllegalArgumentException("No tienes permiso sobre esta skill");
        }

        return skill;
    }
}