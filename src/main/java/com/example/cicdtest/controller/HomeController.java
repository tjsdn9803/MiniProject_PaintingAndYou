package com.example.cicdtest.controller;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/api/hello")
    public String helloWorld() {
        return "Hello world CI/CD test!!";
    }
}
