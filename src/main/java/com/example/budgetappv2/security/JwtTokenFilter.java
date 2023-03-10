package com.example.budgetappv2.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.budgetappv2.user.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Value("${secret.key}")
    private String KEY;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader == null){
            filterChain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = getusernamePasswordAuthenticationToken(authorizationHeader);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

    public UsernamePasswordAuthenticationToken getusernamePasswordAuthenticationToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(KEY);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("budgetApp")
                .build();
        DecodedJWT decodedJWT = verifier.verify(token.substring(7));

        return new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, Collections.singleton(new SimpleGrantedAuthority(Role.ADMIN.name())));
    }
}
