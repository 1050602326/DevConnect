
package dev.juanv.decconnectcode.controller;

import dev.juanv.decconnectcode.service.JobOfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final JobOfferService jobOfferService;

    public HomeController(JobOfferService jobOfferService) {
        this.jobOfferService = jobOfferService;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute(
                "offers",
                jobOfferService.getActiveOffers()
        );

        return "index";
    }
}
