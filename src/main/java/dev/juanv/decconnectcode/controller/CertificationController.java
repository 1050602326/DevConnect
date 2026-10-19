
package dev.juanv.decconnectcode.controller;

import dev.juanv.decconnectcode.dto.CertificationRequest;
import dev.juanv.decconnectcode.service.CertificationService;
import dev.juanv.decconnectcode.service.SessionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CertificationController {

    private final CertificationService certificationService;
    private final SessionService sessionService;

    public CertificationController(
            CertificationService certificationService,
            SessionService sessionService) {
        this.certificationService = certificationService;
        this.sessionService = sessionService;
    }

    @PostMapping("/profile/certification")
    public String addCertification(
            @Valid @ModelAttribute("certificationRequest")
            CertificationRequest request,
            BindingResult result,
            HttpSession session) {

        Long professionalId = sessionService.getProfessionalId(session);

        if (professionalId == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            return "redirect:/profile?certificationError";
        }

        try {
            certificationService.addCertification(professionalId, request);
            return "redirect:/profile?certificationSuccess";
        } catch (IllegalArgumentException e) {
            return "redirect:/profile?certificationError";
        }
    }
}
