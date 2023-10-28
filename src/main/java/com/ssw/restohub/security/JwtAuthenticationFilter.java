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

// 3rd Class in JWT Authentication Process: Main filter that blocks every incoming request trying to access our locked APIs

//                                    Very Important logic implemented here:
//              1. Check if Token present in requestHeader. If token not present, reject the request immediately.
//              2. Extract username & expiry from token for validation. If anything looks wrong, reject the request immediately.
//              3. If all the above is good, set the authentication for this request in the security context and move on.

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    private JwtHelper jwtHelper;
    private UserDetailsService userDetailsService; // It has loadUserByUsername(). Used to load user's details based on username.

    @Autowired
    private JwtAuthenticationFilter(JwtHelper jwtHelper, UserDetailsService userDetailsService){
        this.jwtHelper = jwtHelper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // We'll get the header from request first, looking for this in header: Authorization = Bearer KJHSGDJHGSJDFLHKLDFSDF
        String requestHeader = request.getHeader("Authorization"); // Getting 'Authorization' property content from header
        logger.info("Header: {}", requestHeader);

        String token = null; // Extracted from requestHeader's 'Authorization' property. for e.g., This part is the token: KJHSGDJHGSJDFLHKLDFSDF
        String username = null; // Extracted from token.

        // If Header's 'Authorization' property is not empty and has 'Bearer' in it, then extracting 'token' and 'username' from it
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

            // Creating and Setting Authentication if Token is Valid
            if(isTokenValid){

                //Creating authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetailsOfCurrentUser, null,userDetailsOfCurrentUser.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // Adding our http request for context

                //Setting authentication
                SecurityContextHolder.getContext().setAuthentication(authentication); // The moment this authentication is set, the locked APIs can be accessed
            }
        } else { logger.info("Validation failed !!"); }

        // We move on from here like usual. The above filter's main purpose was either to set the Authentication for this request or reject it
        filterChain.doFilter(request,response);
    }
}
