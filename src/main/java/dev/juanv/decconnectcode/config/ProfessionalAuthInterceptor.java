package dev.juanv.decconnectcode.config;

import dev.juanv.decconnectcode.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ProfessionalAuthInterceptor implements HandlerInterceptor {

    private final SessionService sessionService;

    public ProfessionalAuthInterceptor(
            SessionService sessionService
    ) {
        this.sessionService = sessionService;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        HttpSession session =
                request.getSession(false);

        if (session == null ||
                !sessionService.isAuthenticated(session)) {

            response.sendRedirect("/login");
            return false;
        }

        return true;
    }
}