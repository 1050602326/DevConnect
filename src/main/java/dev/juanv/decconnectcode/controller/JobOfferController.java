
package dev.juanv.decconnectcode.controller;

import dev.juanv.decconnectcode.model.JobOffer;
import dev.juanv.decconnectcode.service.JobOfferService;
import dev.juanv.decconnectcode.service.SessionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/company/offers")
public class JobOfferController {

    private final JobOfferService jobOfferService;
    private final SessionService sessionService;

    public JobOfferController(JobOfferService jobOfferService,
                              SessionService sessionService) {
        this.jobOfferService = jobOfferService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public String listOffers(HttpSession session, Model model) {
        Long companyId = sessionService.getCompanyId(session);
        model.addAttribute("offers",
                jobOfferService.getCompanyOffers(companyId));
        return "company/offers/list";
    }

    @GetMapping("/new")
    public String newOffer(HttpSession session, Model model) {
        Long companyId = sessionService.getCompanyId(session);

        if (companyId == null) {
            return "redirect:/company/login";
        }

        model.addAttribute("offer", new JobOffer());
        return "company/offers/form";
    }


    @PostMapping("/save")
    public String saveOffer(@ModelAttribute JobOffer offer,
                            HttpSession session) {

        Long companyId = sessionService.getCompanyId(session);

        if (offer.getTitle() == null || offer.getTitle().isBlank()
                || offer.getDescription() == null
                || offer.getDescription().isBlank()) {
            return "redirect:/company/offers/new?error=required";
        }

        offer.setTitle(offer.getTitle().trim());
        offer.setDescription(offer.getDescription().trim());

        jobOfferService.createOffer(offer, companyId);

        return "redirect:/company/offers";
    }


    @GetMapping("/edit/{id}")
    public String editOffer(@PathVariable Long id,
                            HttpSession session, Model model) {
        Long companyId = sessionService.getCompanyId(session);

        try {
            model.addAttribute("offer",
                    jobOfferService.getOwnedOffer(id, companyId));
            return "company/offers/form";
        } catch (IllegalArgumentException e) {
            return "redirect:/company/offers?error=unauthorized";
        }
    }


    @PostMapping("/update/{id}")
    public String updateOffer(@PathVariable Long id,
                              @ModelAttribute JobOffer offer,
                              HttpSession session) {

        Long companyId = sessionService.getCompanyId(session);

        if (offer.getTitle() == null || offer.getTitle().isBlank()
                || offer.getDescription() == null
                || offer.getDescription().isBlank()) {
            return "redirect:/company/offers/edit/" + id + "?error=required";
        }

        offer.setTitle(offer.getTitle().trim());
        offer.setDescription(offer.getDescription().trim());

        try {
            jobOfferService.updateOffer(id, companyId, offer);
            return "redirect:/company/offers";
        } catch (IllegalArgumentException e) {
            return "redirect:/company/offers?error=unauthorized";
        }
    }


    @PostMapping("/status/{id}")
    public String changeStatus(@PathVariable Long id,
                               @RequestParam boolean active,
                               HttpSession session) {
        Long companyId = sessionService.getCompanyId(session);

        try {
            jobOfferService.changeStatus(id, companyId, active);
            return "redirect:/company/offers";
        } catch (IllegalArgumentException e) {
            return "redirect:/company/offers?error=unauthorized";
        }
    }
}
