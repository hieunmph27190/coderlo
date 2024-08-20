package com.hieucoder.coderlo.dto.respone;

import java.util.Date;
import java.util.Set;

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
public class UserResponse {
    String id;
    String userName;
    Set<Role> roles;

    String name;

    Date birthday;

    String email;
}
