package com.hieucoder.coderlo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.hieucoder.coderlo.dto.request.UserCreationRequest;
import com.hieucoder.coderlo.dto.request.UserUpdateRequest;
import com.hieucoder.coderlo.dto.respone.UserResponse;
import com.hieucoder.coderlo.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest userCreationRequest);

    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);
}
