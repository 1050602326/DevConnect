
package dev.juanv.decconnectcode.controller;

import dev.juanv.decconnectcode.model.Company;
import dev.juanv.decconnectcode.service.CompanyService;
import dev.juanv.decconnectcode.service.SessionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;
    private final SessionService sessionService;

    public CompanyController(
            CompanyService companyService,
            SessionService sessionService) {
        this.companyService = companyService;
        this.sessionService = sessionService;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("company", new Company());
        return "company/register";
    }

    @PostMapping("/register")
    public String register(
            @ModelAttribute Company company,
            Model model) {
        try {
            companyService.registerCompany(company);
            return "redirect:/company/login?registered";
        } catch (IllegalArgumentException e) {
            model.addAttribute("company", company);
            model.addAttribute("error", e.getMessage());
            return "company/register";
        }
    }

    @GetMapping("/login")
    public String loginForm() {
        return "company/login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        try {
            Company company = companyService.authenticate(email, password);
            sessionService.loginCompany(session, company.getId());
            return "redirect:/company/dashboard";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "company/login";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Company company = companyService.getCompany(
                sessionService.getCompanyId(session));

        model.addAttribute("company", company);
        return "company/dashboard";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Company company = companyService.getCompany(
                sessionService.getCompanyId(session));

        model.addAttribute("company", company);
        return "company/profile";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        sessionService.logout(session);
        return "redirect:/company/login";
    }
}
