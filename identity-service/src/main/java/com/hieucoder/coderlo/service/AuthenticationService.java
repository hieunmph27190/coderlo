package com.hieucoder.coderlo.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hieucoder.coderlo.dto.request.AuthenticationRequest;
import com.hieucoder.coderlo.dto.request.IntrospectRequest;
import com.hieucoder.coderlo.dto.request.LogoutRequest;
import com.hieucoder.coderlo.dto.request.RefreshRequest;
import com.hieucoder.coderlo.dto.response.AuthenticationResponse;
import com.hieucoder.coderlo.dto.response.IntrospectResponse;
import com.hieucoder.coderlo.entity.Token;
import com.hieucoder.coderlo.entity.User;
import com.hieucoder.coderlo.exception.AppException;
import com.hieucoder.coderlo.exception.ErrorCode;
import com.hieucoder.coderlo.repository.TokenRepository;
import com.hieucoder.coderlo.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class AuthenticationService {
    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    UserRepository userRepository;
    TokenRepository tokenRepository;

    public AuthenticationResponse authentication(AuthenticationRequest request) {

        var user = userRepository
                .findByUserName(request.getUserName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(user);
        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    private String generateToken(User user) {
        StringBuffer scope = new StringBuffer();
        user.getRoles().stream().forEach(role -> {
            scope.append("ROLE_" + role.getName() + " ");
            if (!CollectionUtils.isEmpty(role.getPermissions()))
                role.getPermissions().stream().forEach(permission -> {
                    scope.append(permission.getName() + " ");
                });
        });

        Date expiryTime =
                new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli());

        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserName())
                .issuer("coderlo.com")
                .issueTime(new Date())
                .expirationTime(expiryTime)
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", scope.toString().trim())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));

            String tokenStr = jwsObject.serialize();
            Token token = Token.builder()
                    .id(jwtClaimsSet.getJWTID())
                    .userName(user.getUserName())
                    .token(tokenStr)
                    .expiryTime(expiryTime)
                    .status(1)
                    .build();
            tokenRepository.save(token);
            return tokenStr;
        } catch (JOSEException e) {
            log.info("không tạo được token");
            log.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public IntrospectResponse introspect(IntrospectRequest request) {
        var token = request.getToken();
        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (AppException | JOSEException | ParseException e) {
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }

    public boolean logout(LogoutRequest request) {
        try {
            var signToken = verifyToken(request.getToken(), true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Token token = tokenRepository.findById(jit).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
            token.setStatus(0);

            tokenRepository.save(token);
            return true;
        } catch (AppException | ParseException | JOSEException exception) {
            log.info("Token already expired");
        }
        return false;
    }

    public Integer logoutAll(LogoutRequest request) {
        try {
            var signToken = verifyToken(request.getToken(), true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Token token = tokenRepository.findById(jit).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
            Integer count = tokenRepository.updateStatusByUserName(0, token.getUserName());

            return count;
        } catch (AppException | ParseException | JOSEException exception) {
            log.info("Token already expired");
        }
        return 0;
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken(), true);

        var jit = signedJWT.getJWTClaimsSet().getJWTID();

        Token tokenOld = tokenRepository.findById(jit).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        tokenOld.setStatus(0);
        tokenRepository.save(tokenOld);

        var username = signedJWT.getJWTClaimsSet().getSubject();

        var user =
                userRepository.findByUserName(username).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        var token = generateToken(user);
        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                        .getJWTClaimsSet()
                        .getIssueTime()
                        .toInstant()
                        .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                        .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);

        Token tokeDb = tokenRepository
                .findById(signedJWT.getJWTClaimsSet().getJWTID())
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        if (tokeDb.getStatus() <= 0) throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }
}
