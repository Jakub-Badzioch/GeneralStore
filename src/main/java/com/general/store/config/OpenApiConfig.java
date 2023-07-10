package com.general.store.config;

import com.nimbusds.jwt.JWT;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    // sluzy do konfiguracji swaggera zeby pokazywal jakie jest security i dodatkowe opisy
    // jest to potrzebne zeby mozna bylo korzystac ze swaggera w 100%. bez tego nie bedzie mozna wysylac requstow na chronione endpointy
    @Bean
    public OpenAPI openAPI(@Value("${info.version}") String appVersion) {
        return new OpenAPI()
                .components(new Components()
                        // key = nazwa naszego security
                        .addSecuritySchemes("security jwt", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .info(new Info()
                        .title("show restapi")
                        .description("description")
                        .version(appVersion)
                        .contact(new Contact()
                                .email("email.osoby.ktora.to.napisala@gmail.com")
                                .name("Jakub Badzioch")
                                .url("najczesciej link do gita projektu")));
    }
}
// fajnie jest to wypelnic wrzuc apke na aws i dac rekruterowi link do aws