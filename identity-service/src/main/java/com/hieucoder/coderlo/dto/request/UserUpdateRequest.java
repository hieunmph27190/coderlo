package com.hieucoder.coderlo.dto.request;

import java.util.Set;

import jakarta.validation.constraints.Size;

import com.hieucoder.coderlo.entity.Role;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String id;

    @Size(min = 5, max = 20, message = "UserName 5-20 kí tự")
    String userName;

    @Size(min = 5, max = 20, message = "Password 5-20 kí tự")
    String password;

    Set<Role> roles;
}
