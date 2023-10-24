package com.ssw.restohub.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/////////////// 2. Just a class that will help us: generateToken() and validateToken()  /////////////////////

@Component
public class JwtHelper {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 *60; // Token validity to 5 hrs, calculating seconds in 5 hours: 5 hrs x 60 min x 60 sec
    private String secret = "AKSHJGLAJHDGSWFSFDASDFADFSAFAFD"; //Random letters

    // Generate token for user  [FUNCTION USED OUTSIDE THIS CLASS]
    // Pass the user's UserDetails in this function and the token will be created.
    // See AppConfig to get an Idea on how UserDetails is created for a user
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    // Validate token of user [FUNCTION USED OUTSIDE THIS CLASS]
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // While creating the token -
    // 1. Define claims of the token: Issuer, Expiration, Subject and the ID
    // 2. Sign the JWT using the HS512 algorithm and secret key
    // 3. According to JWS Compact Serialization (https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1) compact the JWT
    // into a URL-Safe String
    private String doGenerateToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    // Retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // Check if the token is expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // To retrieve any info from token we will need secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
