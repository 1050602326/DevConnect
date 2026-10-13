package dev.juanv.decconnectcode.controller;

import dev.juanv.decconnectcode.dto.LoginRequest;
import dev.juanv.decconnectcode.dto.RegisterRequest;
import dev.juanv.decconnectcode.model.Professional;
import dev.juanv.decconnectcode.service.ProfessionalService;
import dev.juanv.decconnectcode.service.SessionService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final ProfessionalService professionalService;
    private final SessionService sessionService;

    public AuthController(
            ProfessionalService professionalService,
            SessionService sessionService
    ) {
        this.professionalService = professionalService;
        this.sessionService = sessionService;
    }

    // =========================
    // REGISTRO
    // =========================

    @GetMapping("/register")
    public String registerForm(Model model) {

        model.addAttribute(
                "registerRequest",
                new RegisterRequest()
        );

        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid
            @ModelAttribute RegisterRequest registerRequest,
            BindingResult result,
            Model model
    ) {

        if (result.hasErrors()) {
            return "register";
        }

        try {

            professionalService.register(
                    registerRequest
            );

            return "redirect:/register?success";

        } catch (IllegalArgumentException e) {

            model.addAttribute(
                    "error",
                    e.getMessage()
            );

            return "register";
        }
    }

    // =========================
    // LOGIN
    // =========================

    @GetMapping("/login")
    public String loginForm(Model model) {

        model.addAttribute(
                "loginRequest",
                new LoginRequest()
        );

        return "login";
    }

    @PostMapping("/login")
    public String login(
            @Valid
            @ModelAttribute LoginRequest loginRequest,
            BindingResult result,
            Model model,
            HttpSession session
    ) {

        if (result.hasErrors()) {
            return "login";
        }

        try {

            Professional professional =
                    professionalService.authenticate(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    );

            sessionService.login(
                    session,
                    professional.getId()
            );

            return "redirect:/profile";

        } catch (IllegalArgumentException e) {

            model.addAttribute(
                    "error",
                    e.getMessage()
            );

            return "login";
        }
    }



    @PostMapping("/logout")
    public String logout(HttpSession session) {

        sessionService.logout(session);

        return "redirect:/login";
    }
}