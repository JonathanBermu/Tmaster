package com.example.Users.Interceptors;

import com.example.Users.config.Json;
import com.fasterxml.jackson.databind.JsonNode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.security.Key;
import java.util.Base64;

@Component
public class HttpInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        System.out.println(method);
        System.out.println(url);
        if(!url.contains("login")
                && !url.contains("send_recover_password_email")
                && !url.contains("recover_password")
                && !url.contains("tournaments/general")
                && !url.contains("error")
                && !method.equals("OPTIONS")
        ) {
            String auth = request.getHeader("Authorization");
            auth = auth.replace("Bearer ", "");
            String secret =  "MichaelJacksonIsVeryVeryVeryBlackIMnOtGoingtolie";
            Key secretKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                    SignatureAlgorithm.HS256.getJcaName());
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(auth);
        }
        System.out.println("pasando");
        return true;
    }
}
