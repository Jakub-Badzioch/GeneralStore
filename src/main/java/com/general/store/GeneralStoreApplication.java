package com.general.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class GeneralStoreApplication {
    /*
    todo
     4 intefejsy funkcyjne na pamiec: predicate, supplier, function, consumer
     sciagnij libre office
    */
    public static void main(String[] args) {
        SpringApplication.run(GeneralStoreApplication.class, args);
    }
}
// auth to skro od authentication

// todo w dokumentacji api powinny byc tylko dtosy. zadnych dao
