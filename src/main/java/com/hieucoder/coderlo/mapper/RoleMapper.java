package com.hieucoder.coderlo.mapper;

import com.hieucoder.coderlo.entity.Permission;
import org.mapstruct.Mapper;

import com.hieucoder.coderlo.dto.request.RoleRequest;
import com.hieucoder.coderlo.dto.respone.RoleResponse;
import com.hieucoder.coderlo.entity.Role;
import org.mapstruct.Mapping;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", source = "permissions")
    Role toRole(RoleRequest request);
    @Mapping(target = "permissions", source = "permissions")
    RoleResponse toRoleResponse(Role role);

    default Set<Permission> mapPermissions(Set<Permission> permissions) {
        if (permissions == null) {
            return null;
        }
        return new HashSet<>(permissions);
    }
}
