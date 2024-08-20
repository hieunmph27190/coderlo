package com.hieucoder.coderlo.mapper;

import com.hieucoder.coderlo.dto.request.UserCreationRequest;
import com.hieucoder.coderlo.dto.request.UserProfileCreationRequest;
import com.hieucoder.coderlo.dto.request.UserUpdateRequest;
import com.hieucoder.coderlo.dto.respone.UserProfileResponse;
import com.hieucoder.coderlo.dto.respone.UserResponse;
import com.hieucoder.coderlo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
     void update(@MappingTarget UserResponse userResponse, UserProfileResponse userProfileResponse);
}
