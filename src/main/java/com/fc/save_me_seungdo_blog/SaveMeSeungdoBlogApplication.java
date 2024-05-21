package com.fc.save_me_seungdo_blog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.File;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
public class SaveMeSeungdoBlogApplication {

    @Value("${file.path}")
    public String path;

    public static void main(String[] args) {

        SpringApplication.run(SaveMeSeungdoBlogApplication.class, args);
    }

    @Bean
    CommandLineRunner init() {
        return args -> {
            File uploadDir = new File("static/posts");
            if (!uploadDir.exists()) {
                boolean created = uploadDir.mkdirs();
                if (created) {
                    log.info("Upload directory created at {}", path);
                } else {
                    log.error("Failed to create upload directory at {}", path);
                }
            }
        };
    }
}
