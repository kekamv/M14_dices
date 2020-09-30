package dices.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;

@Service
@Configuration
public class TokenAuthenticationService {

    private final String TOKEN_PREFIX = "Bearer";
    private final String HEADER_STRING = "Authorization";
    private final Logger log;
    @Value("${jwtSecret}")
    private String jwtSecret;
    @Value("${jwtExpirationInMs}")
    private long jwtExpirationInMs;

    public TokenAuthenticationService (){
        super();
        log = LoggerFactory.getLogger(this.getClass());
    }

    public void addAuthentication (HttpServletResponse res, String username){
        String JWT = generateToken(username);
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + "" + JWT);
    }

    public String generateToken (String username){

        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        return JWT;
    }

    public Authentication getAuthentication(HttpServletRequest req){

        String token = req.getHeader(HEADER_STRING);

        if(token != null){
            String username = getAuthenticationUser(token);
            return username != null ? new UsernamePasswordAuthenticationToken(
                    username, null, Collections.emptyList()) : null;
            }
        return null;
    }

    public String getAuthenticationUser (String token){
        try {
            return Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();
        }
        catch (Exception e){
            log.error("Cannot validate token '{}': error thrown - {}", token, e.getMessage());
        }
        return null;
    }


    public String getUsernameFromRequest(String request){

        String jwt = request.substring(7);
        return getAuthenticationUser(jwt);
    }

}