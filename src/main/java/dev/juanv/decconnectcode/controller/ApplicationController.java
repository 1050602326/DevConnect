package dev.juanv.decconnectcode.controller;

import dev.juanv.decconnectcode.service.ApplicationService;
import dev.juanv.decconnectcode.service.SessionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ApplicationController {

    private final ApplicationService applicationService;
    private final SessionService sessionService;

    public ApplicationController(ApplicationService applicationService,
                                 SessionService sessionService) {
        this.applicationService = applicationService;
        this.sessionService = sessionService;
    }

    @PostMapping("/offers/{jobOfferId}/apply")
    public String apply(@PathVariable Long jobOfferId,
                        HttpSession session,
                        RedirectAttributes ra) {
        Long professionalId = sessionService.getProfessionalId(session);
        if (professionalId == null) return "redirect:/login";

        try {
            applicationService.apply(professionalId, jobOfferId);
            ra.addFlashAttribute("success", "¡Te has postulado correctamente!");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/offers/" + jobOfferId;
    }

    @GetMapping("/professional/applications")
    public String professionalApplications(HttpSession session, Model model) {
        Long professionalId = sessionService.getProfessionalId(session);
        if (professionalId == null) return "redirect:/login";

        model.addAttribute("applications",
                applicationService.getProfessionalApplications(professionalId));
        return "applications/my-applications";
    }

    @GetMapping("/company/applications")
    public String companyApplications(HttpSession session, Model model) {
        Long companyId = sessionService.getCompanyId(session);
        if (companyId == null) return "redirect:/company/login";

        model.addAttribute("applications",
                applicationService.getCompanyApplications(companyId));
        model.addAttribute("statuses", ApplicationService.STATUSES);
        return "company/offers/applicants";
    }

    // Tarea 17
    @PostMapping("/company/applications/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam String status,
                               HttpSession session,
                               RedirectAttributes ra) {
        Long companyId = sessionService.getCompanyId(session);
        if (companyId == null) return "redirect:/company/login";

        try {
            applicationService.updateStatus(companyId, id, status);
            ra.addFlashAttribute("success", "Estado actualizado");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/company/applications";
    }
}