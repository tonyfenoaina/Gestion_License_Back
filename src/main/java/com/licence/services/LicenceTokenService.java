package com.licence.services;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.licence.models.LicenceIdentity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
public class LicenceTokenService {
    
    @Value("${key-hash}")
    private String KEY;

    public String generateToken(LicenceIdentity licenceIdentity) throws JsonProcessingException{
        Map<String,Object> claims = new HashMap<>();
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        claims.put("date_activation", simpleDateFormat.format(new Date()));
        claims.put("mode_activation", licenceIdentity.getModeActivation());
        claims.put("licence", licenceIdentity);
    




        return Jwts.builder()
                .setClaims(claims)
                .setSubject("KEY")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(licenceIdentity.getLicence().getEndDate())
                .signWith(SignatureAlgorithm.HS256,KEY.getBytes())
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(KEY.getBytes()).parseClaimsJws(token).getBody();
    }

    public LicenceIdentity getLicenceIdentity(String token) {
        return extractAllClaims(token).get("licence", LicenceIdentity.class);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }





}
