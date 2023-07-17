package com.example.cicdtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CicdtestApplication {

    public static void main(String[] args) {
        SpringApplication.run(CicdtestApplication.class, args);
    }

}
