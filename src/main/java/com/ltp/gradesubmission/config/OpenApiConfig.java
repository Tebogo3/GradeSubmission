package com.ltp.gradesubmission.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @Configuration to define Bean definition
 */
@Configuration
public class OpenApiConfig {
    /**
     * this is Bean definition that return API object
     * Swagger UI will be updated with below bean properties; info, title, description, version
     */
    @Bean
    public OpenAPI OpenAPI(){
        return new OpenAPI()
                        .info(new Info()
                        .title("Grade Submission API")
                        .description("An API that manages Grade submissions")
                        .version("v1.0"));
    }
}
