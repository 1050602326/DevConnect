package dev.juanv.decconnectcode.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private static final String USER_ID = "professionalId";
    private static final String COMPANY_ID = "companyId";

    // ---------- Profesional ----------

    public void login(HttpSession session, Long professionalId) {
        session.setAttribute(USER_ID, professionalId);
    }

    public Long getProfessionalId(HttpSession session) {
        Object value = session.getAttribute(USER_ID);
        return value instanceof Long id ? id : null;
    }

    public boolean isAuthenticated(HttpSession session) {
        return getProfessionalId(session) != null;
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    // ---------- Empresa ----------

    public void loginCompany(HttpSession session, Long companyId) {
        session.setAttribute(COMPANY_ID, companyId);
    }

    public Long getCompanyId(HttpSession session) {
        Object value = session.getAttribute(COMPANY_ID);
        return value instanceof Long id ? id : null;
    }

    public boolean isCompanyAuthenticated(HttpSession session) {
        return getCompanyId(session) != null;
    }

    public void logoutCompany(HttpSession session) {
        session.removeAttribute(COMPANY_ID);
    }
}