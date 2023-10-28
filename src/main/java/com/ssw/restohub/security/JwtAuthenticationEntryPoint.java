package com.ssw.restohub.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.PrintWriter;

// 1st Class in JWT Authentication Process

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    //This method is called when an exception is thrown when an unauthenticated request is trying to access our locked APIs
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        //Just sending back a message with http response when the request fails
        PrintWriter writer = response.getWriter();
        writer.println("Access Denied!!" + authException.getMessage()); //getting auth denied exception
    }
}
