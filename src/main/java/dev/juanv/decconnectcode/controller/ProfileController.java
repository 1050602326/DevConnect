package dev.juanv.decconnectcode.controller;

import dev.juanv.decconnectcode.dto.ProfileRequest;
import dev.juanv.decconnectcode.model.ProfessionalProfile;
import dev.juanv.decconnectcode.service.ProfileService;
import dev.juanv.decconnectcode.service.SessionService;
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


    public ProfileController(ProfileService profileService, SessionService sessionService) {
        this.profileService = profileService;
        this.sessionService = sessionService;
    }


    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model){

        Long professionalId = sessionService.getProfessionalId(session);

        if (professionalId == null){
            return "redirect:/login";
        }


        Optional<ProfessionalProfile> existingProfile = profileService.findProfile(professionalId);

        ProfileRequest request = new ProfileRequest();

        existingProfile.ifPresent(profile -> {
            request.setHeadline(profile.getHeadline());
            request.setDescription(profile.getDescription());
            request.setExperience(profile.getExperience());
            request.setCity(profile.getCity());
        });

        model.addAttribute("profileRequest",request);
        model.addAttribute("hasProdile",existingProfile.isPresent());


        return "profile";

    }

    @PostMapping("/profile")
    public String saveProfile(
            @Valid @ModelAttribute("profileRequest") ProfileRequest request,
            BindingResult result,
            HttpSession session,
            Model model
    ) {
        Long professionalId = sessionService.getProfessionalId(session);

        if (professionalId == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            model.addAttribute(
                    "hasProfile",
                    profileService.findProfile(professionalId).isPresent()
            );
            return "profile";
        }

        try {
            profileService.saveProfile(professionalId, request);
            return "redirect:/profile?success";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute(
                    "hasProfile",
                    profileService.findProfile(professionalId).isPresent()
            );
            return "profile";
        }
    }
}
