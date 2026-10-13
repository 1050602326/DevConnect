package dev.juanv.decconnectcode.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private static final String USER_ID = "professionalId";

    public void login(HttpSession session, Long professionalId) {
        session.setAttribute(USER_ID, professionalId);
    }

    public Long getProfessionalId(HttpSession session) {
        Object value = session.getAttribute(USER_ID);

        if (value == null) {
            return null;
        }

        return (Long) value;
    }

    public boolean isAuthenticated(HttpSession session) {
        return getProfessionalId(session) != null;
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }
}