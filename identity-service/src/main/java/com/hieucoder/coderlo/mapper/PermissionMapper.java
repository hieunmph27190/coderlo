package com.hieucoder.coderlo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.hieucoder.coderlo.dto.request.PermissionRequest;
import com.hieucoder.coderlo.dto.response.PermissionResponse;
import com.hieucoder.coderlo.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    void updatePermission(@MappingTarget Permission permission, PermissionRequest request);

    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
