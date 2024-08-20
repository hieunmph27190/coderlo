package com.hieucoder.profile_service.service;


import com.hieucoder.profile_service.dto.request.UserProfileCreationRequest;
import com.hieucoder.profile_service.dto.request.UserProfileUpdateRequest;
import com.hieucoder.profile_service.entity.UserProfile;
import com.hieucoder.profile_service.mapper.UserProfileMapper;
import com.hieucoder.profile_service.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileService {
    UserProfileRepository userProfileRepository;

    UserProfileMapper userProfileMapper;

    public UserProfile createRequest(UserProfileCreationRequest request) {
        UserProfile userProfile = userProfileMapper.toUserProfile(request);
        return userProfileRepository.save(userProfile);
    }

    public UserProfile update(UserProfileUpdateRequest request) {
        UserProfile userProfile = new UserProfile();
        userProfileMapper.updateUser(userProfile, request);
        return userProfileRepository.save(userProfile);
    }

    public List<UserProfile> findAll() {
        return userProfileRepository.findAll();
    }

    public Optional<UserProfile> findById(String s) {
        return userProfileRepository.findById(s);
    }


    public void deleteById(String s) {
        userProfileRepository.deleteById(s);
    }

}
