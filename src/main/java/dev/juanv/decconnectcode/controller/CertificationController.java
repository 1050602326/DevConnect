
package dev.juanv.decconnectcode.controller;

import dev.juanv.decconnectcode.dto.CertificationRequest;
import dev.juanv.decconnectcode.service.CertificationService;
import dev.juanv.decconnectcode.service.SessionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PathVariable;

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

    @PostMapping("/profile/certification/{id}/delete")
    public String deleteCertification(
            @PathVariable Long id,
            HttpSession session) {

        Long professionalId =
                sessionService.getProfessionalId(session);

        if (professionalId == null) {
            return "redirect:/login";
        }

        try {

            certificationService.deleteCertification(
                    professionalId,
                    id
            );

            return "redirect:/profile?certificationDeleted";

        } catch (IllegalArgumentException e) {

            return "redirect:/profile?certificationDeleteError";
        }
    }
    @PostMapping("/profile/certification/{id}/edit")
    public String editCertification(
            @PathVariable Long id,
            @Valid @ModelAttribute("certificationRequest")
            CertificationRequest request,
            BindingResult bindingResult,
            HttpSession session
    ) {

        Long professionalId =
                sessionService.getProfessionalId(session);

        if (professionalId == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            return "redirect:/profile?certificationEditError";
        }

        try {

            certificationService.updateCertification(
                    professionalId,
                    id,
                    request
            );

            return "redirect:/profile?certificationUpdated";

        } catch (IllegalArgumentException e) {

            return "redirect:/profile?certificationEditError";
        }
    }

}
