
package dev.juanv.decconnectcode.controller;

import dev.juanv.decconnectcode.service.JobOfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/offers")
public class PublicJobOfferController {

    private final JobOfferService jobOfferService;

    public PublicJobOfferController(JobOfferService jobOfferService) {
        this.jobOfferService = jobOfferService;
    }

    @GetMapping
    public String listOffers(Model model) {
        model.addAttribute("offers", jobOfferService.getActiveOffers());
        return "offers/list";
    }

    @GetMapping("/{id}")
    public String offerDetail(@PathVariable Long id, Model model) {
        model.addAttribute("offer", jobOfferService.getActiveOffer(id));
        return "offers/detail";
    }
}
