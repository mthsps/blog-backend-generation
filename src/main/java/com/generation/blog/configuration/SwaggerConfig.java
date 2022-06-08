package com.generation.blog.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI createOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Blog project")
                        .description("Blog project to be used as a tool for learning the Spring framework during Module II of the Generation Brazil bootcamp")
                        .version("v0.0.1")
                        .license(new License()
                                .name("Attribution-NonCommercial 4.0 International")
                                .url("https://creativecommons.org/licenses/by-nc/4.0/"))
                        .contact(new Contact()
                                .name("Matheus")
                                .url("https://github.com/mthsps")))
                        .externalDocs(new ExternalDocumentation()
                                .description("GitHub")
                                .url("https://github.com/mthsps/blog-backend-generation"));

    }

    @Bean public OpenApiCustomiser openApiCustomiser() {
        return openApi -> {
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {

                ApiResponses apiResponses = operation.getResponses();
                apiResponses.addApiResponse("200", createApiResponse("Successful"));
                apiResponses.addApiResponse("201", createApiResponse("Object persisted"));
                apiResponses.addApiResponse("204", createApiResponse("Object deleted"));
                apiResponses.addApiResponse("400", createApiResponse("Request error"));
                apiResponses.addApiResponse("401", createApiResponse("Access non authorized"));
                apiResponses.addApiResponse("404", createApiResponse("Not found"));
                apiResponses.addApiResponse("500", createApiResponse("Server error"));
            }));
        };
    }

    private ApiResponse createApiResponse(String message) {
        return new ApiResponse().description(message);
    }

}