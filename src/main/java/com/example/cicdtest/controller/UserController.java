package com.example.cicdtest.controller;

import com.example.cicdtest.dto.LoginRequestDto;
import com.example.cicdtest.dto.Result;
import com.example.cicdtest.dto.SignupRequestDto;
import com.example.cicdtest.entity.UserRoleEnum;
import com.example.cicdtest.security.UserDetailsImpl;
import com.example.cicdtest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<Result> signup(@RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.ok()
                .body(Result.success("회원가입 성공"));
    }

    @PostMapping("/user/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto){
        return userService.login(loginRequestDto);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result> exceptionHandler(Exception e){
        return ResponseEntity.badRequest()
                .body(Result.error(e.getMessage()));
    }

}
