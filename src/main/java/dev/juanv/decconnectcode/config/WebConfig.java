
package dev.juanv.decconnectcode.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ProfessionalAuthInterceptor professionalAuthInterceptor;
    private final CompanyAuthInterceptor companyAuthInterceptor;

    public WebConfig(
            ProfessionalAuthInterceptor professionalAuthInterceptor,
            CompanyAuthInterceptor companyAuthInterceptor
    ) {
        this.professionalAuthInterceptor = professionalAuthInterceptor;
        this.companyAuthInterceptor = companyAuthInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // Protección de profesionales
        registry.addInterceptor(professionalAuthInterceptor)
                .addPathPatterns(
                        "/profile",
                        "/profile/**"
                );

        // Protección de empresas
        registry.addInterceptor(companyAuthInterceptor)
                .addPathPatterns(
                        "/company/dashboard",
                        "/company/profile",
                        "/company/offers",
                        "/company/offers/**"
                );
    }
}
