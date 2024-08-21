package com.hieucoder.coderlo.repository.httpclient;

import com.hieucoder.coderlo.config.AuthenticationRequestInterceptor;
import com.hieucoder.coderlo.dto.request.UserProfileCreationRequest;
import com.hieucoder.coderlo.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name ="profile-service" ,url = "${app.services.feign.profile}"
        ,configuration = {AuthenticationRequestInterceptor.class})
public interface UserProfileCliient {

    @PostMapping(value = "/internal/profile",produces = MediaType.APPLICATION_JSON_VALUE)
    public UserProfileResponse createUserProfile(@RequestBody UserProfileCreationRequest userProfile);
}
