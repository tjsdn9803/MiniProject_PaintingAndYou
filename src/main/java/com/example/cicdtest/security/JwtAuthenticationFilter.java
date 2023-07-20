package com.example.cicdtest.security;

import com.example.cicdtest.dto.LoginRequestDto;
import com.example.cicdtest.entity.UserRoleEnum;
import com.example.cicdtest.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
//        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();
        response.setStatus(200);
        String result = "로그인 성공";
        String a = "{\n \"ok\" : "+true+"\n}";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValueAsString(a);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().print(a);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String token = jwtUtil.createToken(username, role);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(400);
        String a = "{\n \"ok\" : "+false+"\n}";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValueAsString(a);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().print(a);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}