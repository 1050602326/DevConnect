package dev.juanv.decconnectcode.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ProfessionalAuthInterceptor
            professionalAuthInterceptor;

    public WebConfig(
            ProfessionalAuthInterceptor professionalAuthInterceptor
    ) {
        this.professionalAuthInterceptor =
                professionalAuthInterceptor;
    }

    @Override
    public void addInterceptors(
            InterceptorRegistry registry
    ) {

        registry
                .addInterceptor(professionalAuthInterceptor)
                .addPathPatterns(
                        "/profile",
                        "/profile/**"
                );
    }
}