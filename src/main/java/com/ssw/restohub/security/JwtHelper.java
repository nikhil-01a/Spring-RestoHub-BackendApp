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

// 2nd Class in Jwt Authentication Process: It mainly helps us generateToken() and validateToken()

@Component
public class JwtHelper {

          //////////////////////////////////////// GENERATING TOKEN: FLOW //////////////////////////////////////////////

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 *60; // Change as per requirement: Setting 5 hrs: calculating seconds for it.

    // Random letters, keep it big enough so that it exceeds 96 bits, otherwise exception can occur.
    // Used when we make our token, TIP: put it in app.properties
    private String secret = "AKSHJGLAJHDGSWFSFDASDFADFSAFAFDHJGKJHSGDKJHGSKLJHDGLSKJGDLSKJGDLJKSGLKDJSGKJDGLSKJDJSKLFD";

    // [FUNCTION USED OUTSIDE THIS CLASS] Pass the user's UserDetails in this function and the token will be created.
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
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

           /////////////////////////////////////// VALIDATING TOKEN: FLOW //////////////////////////////////////////////

    // [FUNCTION USED OUTSIDE THIS CLASS] Pass the user's UserDetails and token from request Header in this function to validate it
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Check if the token is expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // Retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
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
