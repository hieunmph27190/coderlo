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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/profile")
public class ProfileController {
    UserProfileService userProfileService;
    UserProfileMapper userProfileMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public List<UserProfile> findAll() {
        return userProfileService.findAll();
    }


}
