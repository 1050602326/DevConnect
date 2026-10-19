
package dev.juanv.decconnectcode.controller;

import dev.juanv.decconnectcode.dto.CertificationRequest;
import dev.juanv.decconnectcode.dto.EducationRequest;
import dev.juanv.decconnectcode.dto.ProfileRequest;
import dev.juanv.decconnectcode.dto.SkillRequest;
import dev.juanv.decconnectcode.model.ProfessionalProfile;
import dev.juanv.decconnectcode.service.CertificationService;
import dev.juanv.decconnectcode.service.EducationService;
import dev.juanv.decconnectcode.service.ProfileService;
import dev.juanv.decconnectcode.service.SessionService;
import dev.juanv.decconnectcode.service.SkillService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class ProfileController {

    private final ProfileService profileService;
    private final SessionService sessionService;
    private final EducationService educationService;
    private final SkillService skillService;
    private final CertificationService certificationService;

    public ProfileController(
            ProfileService profileService,
            SessionService sessionService,
            EducationService educationService,
            SkillService skillService,
            CertificationService certificationService) {

        this.profileService = profileService;
        this.sessionService = sessionService;
        this.educationService = educationService;
        this.skillService = skillService;
        this.certificationService = certificationService;
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {

        Long professionalId = sessionService.getProfessionalId(session);

        if (professionalId == null) {
            return "redirect:/login";
        }

        Optional<ProfessionalProfile> existingProfile =
                profileService.findProfile(professionalId);

        ProfileRequest request = new ProfileRequest();

        existingProfile.ifPresent(profile -> {
            request.setHeadline(profile.getHeadline());
            request.setDescription(profile.getDescription());
            request.setExperience(profile.getExperience());
            request.setCity(profile.getCity());
        });

        model.addAttribute("profileRequest", request);
        model.addAttribute("hasProfile", existingProfile.isPresent());

        // Formularios de la HU-04
        model.addAttribute("educationRequest", new EducationRequest());
        model.addAttribute("skillRequest", new SkillRequest());
        model.addAttribute("certificationRequest", new CertificationRequest());

        // Registros guardados del usuario autenticado
        model.addAttribute(
                "educations",
                educationService.findByProfessionalId(professionalId));

        model.addAttribute(
                "skills",
                skillService.findByProfessionalId(professionalId));

        model.addAttribute(
                "certifications",
                certificationService.findByProfessionalId(professionalId));

        return "profile";
    }

    @PostMapping("/profile")
    public String saveProfile(
            @Valid @ModelAttribute("profileRequest") ProfileRequest request,
            BindingResult result,
            HttpSession session,
            Model model) {

        Long professionalId = sessionService.getProfessionalId(session);

        if (professionalId == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            prepareHu04Model(model, professionalId);
            model.addAttribute(
                    "hasProfile",
                    profileService.findProfile(professionalId).isPresent());
            return "profile";
        }

        try {
            profileService.saveProfile(professionalId, request);
            return "redirect:/profile?success";

        } catch (IllegalArgumentException e) {
            prepareHu04Model(model, professionalId);
            model.addAttribute("error", e.getMessage());
            model.addAttribute(
                    "hasProfile",
                    profileService.findProfile(professionalId).isPresent());
            return "profile";
        }
    }

    private void prepareHu04Model(Model model, Long professionalId) {

        model.addAttribute("educationRequest", new EducationRequest());
        model.addAttribute("skillRequest", new SkillRequest());
        model.addAttribute("certificationRequest", new CertificationRequest());

        model.addAttribute(
                "educations",
                educationService.findByProfessionalId(professionalId));

        model.addAttribute(
                "skills",
                skillService.findByProfessionalId(professionalId));

        model.addAttribute(
                "certifications",
                certificationService.findByProfessionalId(professionalId));
    }
}
