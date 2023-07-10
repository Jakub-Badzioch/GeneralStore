package com.general.store.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedHeaders("*")
                .allowedMethods("*");
        // /** = bedzie mozliwosc komunikacji ze wszystkimi endpointami na backendzie
        // .allowedHeaders("*") = wszystkie hedery beda akceptowalne
        // .allowedMethods("*") = wszystkie metoy beda wspierane
    }
}
