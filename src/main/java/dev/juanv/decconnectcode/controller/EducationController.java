
package dev.juanv.decconnectcode.controller;

import dev.juanv.decconnectcode.dto.EducationRequest;
import dev.juanv.decconnectcode.service.EducationService;
import dev.juanv.decconnectcode.service.SessionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EducationController {

    private final EducationService educationService;
    private final SessionService sessionService;

    public EducationController(
            EducationService educationService,
            SessionService sessionService) {
        this.educationService = educationService;
        this.sessionService = sessionService;
    }

    @PostMapping("/profile/education")
    public String addEducation(
            @Valid @ModelAttribute("educationRequest")
            EducationRequest request,
            BindingResult result,
            HttpSession session) {

        Long professionalId = sessionService.getProfessionalId(session);

        if (professionalId == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            return "redirect:/profile?educationError";
        }

        try {
            educationService.addEducation(professionalId, request);
            return "redirect:/profile?educationSuccess";
        } catch (IllegalArgumentException e) {
            return "redirect:/profile?educationError";
        }
    }
}
