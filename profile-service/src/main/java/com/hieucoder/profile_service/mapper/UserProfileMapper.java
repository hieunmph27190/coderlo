package com.hieucoder.profile_service.mapper;


import com.hieucoder.profile_service.dto.request.UserProfileCreationRequest;
import com.hieucoder.profile_service.dto.request.UserProfileUpdateRequest;
import com.hieucoder.profile_service.dto.response.UserProfileResponse;
import com.hieucoder.profile_service.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(UserProfileCreationRequest userProfileCreationRequest);

    UserProfileResponse toUserProfileResponse(UserProfile user);

    void updateUser(@MappingTarget UserProfile userProfile, UserProfileUpdateRequest userProfileUpdateRequest);
}
