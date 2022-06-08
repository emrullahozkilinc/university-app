package com.example.universityapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableWebMvc
public class UniversityAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniversityAppApplication.class, args);
    }

}
