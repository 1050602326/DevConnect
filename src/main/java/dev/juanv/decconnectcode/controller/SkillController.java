
package dev.juanv.decconnectcode.controller;

import dev.juanv.decconnectcode.dto.SkillRequest;
import dev.juanv.decconnectcode.service.SessionService;
import dev.juanv.decconnectcode.service.SkillService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SkillController {

    private final SkillService skillService;
    private final SessionService sessionService;

    public SkillController(
            SkillService skillService,
            SessionService sessionService) {
        this.skillService = skillService;
        this.sessionService = sessionService;
    }

    @PostMapping("/profile/skill")
    public String addSkill(
            @Valid @ModelAttribute("skillRequest")
            SkillRequest request,
            BindingResult result,
            HttpSession session) {

        Long professionalId = sessionService.getProfessionalId(session);

        if (professionalId == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            return "redirect:/profile?skillError";
        }

        try {
            skillService.addSkill(professionalId, request);
            return "redirect:/profile?skillSuccess";
        } catch (IllegalArgumentException e) {
            return "redirect:/profile?skillError";
        }
    }
}
