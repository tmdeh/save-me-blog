package com.delivery.save_me_seungdo_blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SaveMeSeungdoBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaveMeSeungdoBlogApplication.class, args);
    }

}
