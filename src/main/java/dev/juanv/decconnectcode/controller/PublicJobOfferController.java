package dev.juanv.decconnectcode.controller;

import dev.juanv.decconnectcode.service.ApplicationService;
import dev.juanv.decconnectcode.service.JobOfferService;
import dev.juanv.decconnectcode.service.SessionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/offers")
public class PublicJobOfferController {

    private final JobOfferService jobOfferService;
    private final ApplicationService applicationService;
    private final SessionService sessionService;

    public PublicJobOfferController(
            JobOfferService jobOfferService,
            ApplicationService applicationService,
            SessionService sessionService) {
        this.jobOfferService = jobOfferService;
        this.applicationService = applicationService;
        this.sessionService = sessionService;
    }

    // Listar ofertas
    @GetMapping({"", "/list"})
    public String listOffers(HttpSession session, Model model) {
        model.addAttribute("offers", jobOfferService.getActiveOffers());
        model.addAttribute("loggedIn",
                sessionService.getProfessionalId(session) != null);
        model.addAttribute("isCompany",
                sessionService.getCompanyId(session) != null);
        return "offers/list";
    }

    // Mostrar detalles de una oferta
    @GetMapping("/{id:\\d+}")
    public String offerDetail(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        model.addAttribute("offer", jobOfferService.getActiveOffer(id));

        Long professionalId = sessionService.getProfessionalId(session);
        Long companyId = sessionService.getCompanyId(session);

        model.addAttribute("loggedIn", professionalId != null);
        model.addAttribute("isCompany", companyId != null);

        model.addAttribute("alreadyApplied",
                professionalId != null
                        && applicationService.hasApplied(professionalId, id));

        return "offers/detail";
    }
}