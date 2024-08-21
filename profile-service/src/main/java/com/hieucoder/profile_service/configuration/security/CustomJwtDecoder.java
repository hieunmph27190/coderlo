package com.hieucoder.profile_service.configuration.security;

import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class CustomJwtDecoder implements JwtDecoder {
    @Override
    public Jwt decode(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return new Jwt(token,signedJWT.getJWTClaimsSet().getIssueTime().toInstant()
                    ,signedJWT.getJWTClaimsSet().getExpirationTime().toInstant()
                    ,signedJWT.getHeader().toJSONObject()
                    ,signedJWT.getJWTClaimsSet().getClaims()
                    );
        } catch (ParseException|NullPointerException e) {
            throw new JwtException("Invalid Token");
        }
    }
}
