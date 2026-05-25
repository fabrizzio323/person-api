package com.fabrizio.udemy.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {


    @Bean
    OpenAPI customOpenApi(){
        Server server = new Server().url("/");
       return new OpenAPI()
               .servers(List.of(server))
               .info(new Info()
                       .title("Helllo Swagger, OpenApi")
                       .version("1.0.0")
                       .description("This is a sample Spring Boot RESTful service using springdoc-openapi and")
                       .license(new License()
                               .name("Apache 2.0")
                               .url("https://personal-app")));
    }
}
