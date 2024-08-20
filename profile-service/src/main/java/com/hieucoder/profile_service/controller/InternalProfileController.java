package com.hieucoder.profile_service.controller;


import com.hieucoder.profile_service.dto.request.UserProfileCreationRequest;
import com.hieucoder.profile_service.dto.request.UserProfileUpdateRequest;
import com.hieucoder.profile_service.dto.response.UserProfileResponse;
import com.hieucoder.profile_service.entity.UserProfile;
import com.hieucoder.profile_service.mapper.UserProfileMapper;
import com.hieucoder.profile_service.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/internal/profile")
public class InternalProfileController {
    UserProfileService userProfileService;
    UserProfileMapper userProfileMapper;


    @PostMapping("")
    public UserProfileResponse create(@RequestBody @Valid UserProfileCreationRequest request) {
        return userProfileMapper.toUserProfileResponse(userProfileService.createRequest(request));
    }
    @PutMapping("")
    public UserProfileResponse update(@RequestBody @Valid UserProfileUpdateRequest request) {
        return userProfileMapper.toUserProfileResponse(userProfileService.update(request));
    }

    @GetMapping("")
    public List<UserProfile> findAll() {
        return userProfileService.findAll();
    }

    @GetMapping("/{id}")
    public UserProfile findById(@PathVariable  String id) {
        return userProfileService.findById(id).orElseThrow(
                ()-> new RuntimeException("User Not Found")
        );
    }
    @DeleteMapping("/{id}")
    public UserProfile deleteById(@PathVariable  String id) {
        UserProfile userProfile =  userProfileService.findById(id).orElseThrow(
                ()-> new RuntimeException("User Not Found")
        );
        userProfileService.deleteById(userProfile.getId());
        return userProfile;
    }

}
