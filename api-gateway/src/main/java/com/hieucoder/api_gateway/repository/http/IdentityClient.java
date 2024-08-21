package com.hieucoder.api_gateway.repository.http;

import com.hieucoder.api_gateway.dto.request.IntrospectRequest;
import com.hieucoder.api_gateway.dto.response.ApiResponse;
import com.hieucoder.api_gateway.dto.response.IntrospectResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface IdentityClient {

    @PostExchange(value = "/auth/introspect",contentType = MediaType.APPLICATION_JSON_VALUE )
    Mono< ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request);
}
