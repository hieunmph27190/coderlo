package com.hieucoder.coderlo.controller;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hieucoder.coderlo.dto.request.AuthenticationRequest;
import com.hieucoder.coderlo.dto.request.IntrospectRequest;
import com.hieucoder.coderlo.dto.request.LogoutRequest;
import com.hieucoder.coderlo.dto.request.RefreshRequest;
import com.hieucoder.coderlo.dto.respone.ApiResponse;
import com.hieucoder.coderlo.dto.respone.AuthenticationResponse;
import com.hieucoder.coderlo.dto.respone.IntrospectResponse;
import com.hieucoder.coderlo.exception.AppException;
import com.hieucoder.coderlo.exception.ErrorCode;
import com.hieucoder.coderlo.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/auth")
public class AthenticationControler {
    AuthenticationService authenticationService;

    @PostMapping("/token")
    public ApiResponse getToken(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authentication(request);
        return ApiResponse.builder().result(result).build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder().result(result).build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request) {
        AuthenticationResponse result = null;
        try {
            result = authenticationService.refreshToken(request);
        } catch (ParseException | JOSEException e) {
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/logout")
    ApiResponse<Boolean> logout(@RequestBody LogoutRequest request) {
        Boolean result = authenticationService.logout(request);
        return ApiResponse.<Boolean>builder().result(result).build();
    }

    @PostMapping("/logout_all_token")
    ApiResponse<String> logoutAll(@RequestBody LogoutRequest request) {
        Integer result = authenticationService.logoutAll(request);
        return ApiResponse.<String>builder()
                .result("Logout " + result + " token")
                .build();
    }
}
