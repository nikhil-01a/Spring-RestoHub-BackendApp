package com.ssw.restohub.config;

import com.ssw.restohub.security.JwtAuthenticationEntryPoint;
import com.ssw.restohub.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;  // for Exception
    private JwtAuthenticationFilter jwtAuthenticationFilter;  // for Filter

    @Autowired
    private SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter){
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        return http.build();
    }

}
