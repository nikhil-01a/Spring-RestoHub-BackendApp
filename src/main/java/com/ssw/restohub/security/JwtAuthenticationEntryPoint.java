package com.ssw.restohub.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.PrintWriter;

// 1. Class for Authentication entry point: we create a custom class that implements AuthenticationEntryPoint interface which has an important function "commence"
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    //This method is important because it is automatically invoked when unauthentic user tries to access resources that require authentication
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //Writing some message
        PrintWriter writer = response.getWriter();
        writer.println("Access Denied !!" + authException.getMessage());
    }
}
