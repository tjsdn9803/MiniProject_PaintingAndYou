package com.example.cicdtest.controller;

import com.example.cicdtest.entity.User;
import com.example.cicdtest.security.UserDetailsImpl;
import lombok.Getter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class HomeController {

    @GetMapping("/api/hello")
    public String helloWorld() {
        return "Hello world CI/CD test!!";
    }

    @GetMapping("/api/auth")
    public String userAuth(@AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        return user.getUsername();
    }

}
