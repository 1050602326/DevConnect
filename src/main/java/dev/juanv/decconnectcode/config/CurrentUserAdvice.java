package dev.juanv.decconnectcode.config;

import dev.juanv.decconnectcode.repository.CompanyRepository;
import dev.juanv.decconnectcode.repository.ProfessionalRepository;
import dev.juanv.decconnectcode.service.SessionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CurrentUserAdvice {

    private final SessionService sessionService;
    private final ProfessionalRepository professionalRepository;
    private final CompanyRepository companyRepository;

    public CurrentUserAdvice(SessionService sessionService,
                             ProfessionalRepository professionalRepository,
                             CompanyRepository companyRepository) {
        this.sessionService = sessionService;
        this.professionalRepository = professionalRepository;
        this.companyRepository = companyRepository;
    }

    @ModelAttribute
    public void addCurrentUser(HttpSession session, Model model) {

        Long professionalId = sessionService.getProfessionalId(session);
        if (professionalId != null) {
            professionalRepository.findById(professionalId).ifPresent(p ->
                    model.addAttribute("professionalName",
                            p.getFirstName() + " " + p.getLastName()));
        }

        Long companyId = sessionService.getCompanyId(session);
        if (companyId != null) {
            companyRepository.findById(companyId).ifPresent(c ->
                    model.addAttribute("companyName", c.getName()));
        }
    }
}