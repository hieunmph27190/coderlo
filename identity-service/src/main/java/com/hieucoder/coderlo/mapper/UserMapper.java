package com.hieucoder.coderlo.mapper;

import com.hieucoder.coderlo.dto.request.UserProfileCreationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.hieucoder.coderlo.dto.request.UserCreationRequest;
import com.hieucoder.coderlo.dto.request.UserUpdateRequest;
import com.hieucoder.coderlo.dto.response.UserResponse;
import com.hieucoder.coderlo.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", source = "roles")
    User fromUserCreationRequest(UserCreationRequest userCreationRequest);

    UserResponse toUserResponse(User user);

    UserProfileCreationRequest toUserProfileCreationRequest(User user);
    UserProfileCreationRequest toUserProfileCreationRequest(UserCreationRequest user);

    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);
}
