package com.hieucoder.coderlo.mapper;

import org.mapstruct.Mapper;

import com.hieucoder.coderlo.dto.request.RoleRequest;
import com.hieucoder.coderlo.dto.respone.RoleResponse;
import com.hieucoder.coderlo.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
