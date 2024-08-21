package com.hieucoder.api_gateway.service;


import com.hieucoder.api_gateway.dto.request.IntrospectRequest;
import com.hieucoder.api_gateway.dto.response.ApiResponse;
import com.hieucoder.api_gateway.dto.response.IntrospectResponse;
import com.hieucoder.api_gateway.repository.http.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class IdentityService {

    IdentityClient identityClient;

    public Mono<ApiResponse<IntrospectResponse>> introspect(String token) {
        return identityClient.introspect(IntrospectRequest.builder().token(token).build());
    }
}
