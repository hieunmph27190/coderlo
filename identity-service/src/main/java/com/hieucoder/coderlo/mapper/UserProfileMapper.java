package com.hieucoder.coderlo.mapper;

import com.hieucoder.coderlo.dto.response.UserProfileResponse;
import com.hieucoder.coderlo.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
     void update(@MappingTarget UserResponse userResponse, UserProfileResponse userProfileResponse);
}
