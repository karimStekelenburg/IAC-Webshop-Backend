package com.ElegantDevelopment.iacWebshop.util;


import com.ElegantDevelopment.iacWebshop.exception.ResourceNotFoundException;
import com.ElegantDevelopment.iacWebshop.exception.SessionTimeoutException;
import com.ElegantDevelopment.iacWebshop.model.User;
import com.ElegantDevelopment.iacWebshop.repository.UserRepo;
import com.ElegantDevelopment.iacWebshop.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Date;
import java.util.Properties;

@Component
public class JwtUtil {
    private Resource resource = new ClassPathResource("/application.properties");
    private Properties props = PropertiesLoaderUtils.loadProperties(resource);
    private final byte[] key = props.getProperty("JWTKey").getBytes("UTF-8");

    @Autowired
    private UserRepo userRepo;

    @Autowired
    public JwtUtil() throws IOException {
    }

    /**
     * Takes in a User object and generates a JWT-token holding relevant user information
     *
     * @param user user object
     * @return generated JWT-token
     */
    public String generateJWT(User user) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        return Jwts.builder()
                .setSubject("user")
                .setIssuedAt(now)
                .claim("distortion", nowMillis)
                .claim("userId", user.getId())
                .claim("role", user.getUsername())
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    /**
     * Takes in a JWT-token and attempts to parses it using our key
     *
     * @param token JWT-token sent by the client
     * @return claims object that holds the parsed information
     * @throws UnsupportedEncodingException if the parsing fails (token is invalid)
     */
    public User parseJWT(String token) throws UnsupportedEncodingException {

        Jws<Claims> claims = Jwts.parser()
                .requireSubject("user")
                .setSigningKey(key)
                .parseClaimsJws(token);

        Integer id = claims.getBody().get("userId", Integer.class);
        Long userId = id.longValue();

        return userRepo.findById(userId).orElseThrow(() -> new SessionTimeoutException("session expired"));
    }

}