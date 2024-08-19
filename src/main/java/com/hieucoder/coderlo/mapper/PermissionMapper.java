package com.hieucoder.coderlo.mapper;

import com.hieucoder.coderlo.dto.request.PermissionRequest;
import com.hieucoder.coderlo.dto.request.RoleRequest;
import com.hieucoder.coderlo.dto.respone.PermissionResponse;
import com.hieucoder.coderlo.dto.respone.RoleResponse;
import com.hieucoder.coderlo.entity.Permission;
import com.hieucoder.coderlo.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    void updatePermission(@MappingTarget Permission permission, PermissionRequest request);

    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

}
