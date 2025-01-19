package com.todolist.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration to redirect root and /docs to the Swagger UI page.
 */
@Configuration
public class SwaggerRedirectConfig implements WebMvcConfigurer {

    /**
     * Redirects root and /docs to Swagger UI.
     * @param registry the ViewControllerRegistry
     */
    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/swagger-ui/");
        registry.addRedirectViewController("/docs", "/swagger-ui/");
    }
}
