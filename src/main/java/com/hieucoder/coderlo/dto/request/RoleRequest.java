package com.hieucoder.coderlo.dto.request;

import java.util.Set;

import com.hieucoder.coderlo.entity.Permission;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {
    String name;
    String description;
    Set<Permission> permissions;
}
