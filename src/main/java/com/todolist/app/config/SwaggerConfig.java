package com.todolist.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Swagger configuration for generating API documentation.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Configures the Docket bean to generate Swagger API documentation.
     * @return the configured Docket
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.todolist.app.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiDetails());
    }

    /**
     * Provides additional API details for Swagger.
     * @return the ApiInfo containing metadata for the API
     */
    private ApiInfo apiDetails() {
        return new ApiInfo(
                "To-Do List API",
                "API for managing a simple to-do list application.",
                "1.0",
                "Free to use",
                new Contact("Gbayesola Emmanuel", "http://linkedin.com/in/egbayesola", "egbayesola@gmail.com"),
                "API License",
                "http://licenseurl.com",
                Collections.emptyList()
        );
    }
}
