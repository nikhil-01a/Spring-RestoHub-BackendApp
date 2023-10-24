package com.ssw.restohub.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

// 3. IMPORTANT CLASS: This method will run before every API request and look for the JWT token in the request's header and check it
//                                      Important logic implemented here:
//              Get Token from request,
//              Validate it,
//              GetUsername from token,
//              Load user associated with this token,
//              Set authentication: Tell security to authenticate it

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    private JwtHelper jwtHelper;
    private UserDetailsService userDetailsService; //It has loadUserByUsername(). This service will get an inMemoryUserDetailsManager Object: In our case, we have 3 users,
    // When we fetch a user with its help we'll get it from one of those 3 users

    @Autowired
    private JwtAuthenticationFilter(JwtHelper jwtHelper, UserDetailsService userDetailsService){
        this.jwtHelper = jwtHelper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // We'll get the header from request first, we are fetching this here: Authorization = Bearer KJHSGDJHGSJDFLHKLDFSDF
        String requestHeader = request.getHeader("Authorization");
        logger.info("Header: {}", requestHeader);

        String token = null; // Extracted from requestHeader. for e.g., This part is the token for above: KJHSGDJHGSJDFLHKLDFSDF
        String username = null; // Extracted from token.

        // If Header is proper, extracting 'token' and 'username' from it
        if(requestHeader!=null && requestHeader.startsWith("Bearer")) {

            // Extracting token
            token = requestHeader.substring(7);

            //Extracting username
            try {
                username = this.jwtHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                logger.info("Illegal Argument while extracting username !! ");
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                logger.info("Given Token is expired !! ");
                e.printStackTrace();
            } catch (MalformedJwtException e) {
                logger.info("Token has been tampered !! Invalid Token");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else { logger.info("Invalid Header Value !! "); }

            // Information: Authentication is always set in the 'SecurityContextHolder',
            // SecurityContextHolder is a main guy in Spring Security, which has the 'Context',
            // We get that 'Context' and it has a method 'setAuthentication()' where we set our Authentication

        // Setting Authentication: Checking if 'username exists' and the 'user hasn't been authenticated yet'
        if (username!=null && SecurityContextHolder.getContext().getAuthentication() == null){

            // Fetch user details with the help of username
            UserDetails userDetailsOfCurrentUser = this.userDetailsService.loadUserByUsername(username);

            // Checking Token Validity
            Boolean isTokenValid = this.jwtHelper.validateToken(token, userDetailsOfCurrentUser);

            // Setting Authentication if Token is Valid
            if(isTokenValid){
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetailsOfCurrentUser, null,userDetailsOfCurrentUser.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // HttpServletRequest request, it is a context
                SecurityContextHolder.getContext().setAuthentication(authentication); // The moment this authentication is set, spring security recognizes it and will let the API request do its work
            }
        } else { logger.info("Validation fails !!"); }

        filterChain.doFilter(request,response); // We forward the request whether authentication is set or not.

    }
}
