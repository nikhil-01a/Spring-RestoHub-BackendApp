package com.ssw.restohub.config;

import com.ssw.restohub.security.JwtAuthenticationEntryPoint;
import com.ssw.restohub.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    // SOME IMPORTANT NOTES:
    // UserDetails: Most important class in spring security, this is the one that stores all user details. [Implemented in our Entity File: UserRole]
    // UserDetailsService: Main service of spring security that gives back user's details when you provide it a username. [Implemented in our 'security' package]

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter,UserDetailsService userDetailsService){
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService = userDetailsService;
    }

    private final String[] PUBLIC_URLS = {
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-resources/**",
            "/v3/api-docs",
            "/api/user/save-user",
            "/api/manager/**",
            "/api/staff/**",
            "/api/restaurants/**",
            "/api/reservations/**",
            "/checkout",
            "/charge",
            "/api/payment/charge",
    };

    // SecurityFilterChain: MAIN FILTER OF SPRING SECURITY THROUGH WHICH EVERY REQUEST PASSES
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(csrf -> csrf.disable())
                    .cors(cors -> cors.disable())
                    .authorizeHttpRequests( auth ->
                                auth.requestMatchers("/login").permitAll()
                                    .requestMatchers(PUBLIC_URLS).permitAll()
                                    .anyRequest().authenticated()) // For any other requests apart from above you'll need to be logged / Add more .permitAll() requests above if need be
                    .exceptionHandling( ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint)) // Passing unauthenticated requests through our entry point if exception occurs
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Token authentication helps us avoid keeping sessions. Hence, it is stateless.
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Passing authenticated requests through our jwt filter

        return httpSecurity.build();
    }

    //This takes the user's details from 'UserDetails' and gives it to 'Authentication Manager'
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService); // Setting incoming request's user details
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); // Accepts any incoming request's password in encoded form
        return daoAuthenticationProvider;
    }

    // This will store our passwords in encoded form in db
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Make Authentication Manager a Bean: Utilized in Auth Controller
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
