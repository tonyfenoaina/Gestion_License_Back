package com.licence.security;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.licence.services.TokenService;
import com.licence.services.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JwtFilter extends OncePerRequestFilter{
    
    UserService userService;
    TokenService tokenService;

    public JwtFilter(UserService userService,TokenService tokenService){
        this.userService=userService;
        this.tokenService=tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return ;
		}
        String token;
        String email;
        String role;

        token = authorizationHeader.replace("Bearer ", "");
        try {
            email = tokenService.extractEmail(token);
            role = tokenService.extractRole(token);
        } catch (ExpiredJwtException e) {
            return;
            // TODO: handle exception
        } catch(MalformedJwtException e){
            return;
        }
		filterChain.doFilter(request, response);
    }


}
