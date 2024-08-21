package com.hieucoder.coderlo.entity;

import java.util.Set;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "userName", columnDefinition = "VARCHAR(255) COLLATE utf8_bin")
    String userName;
    String password;

    @ManyToMany
    Set<Role> roles;

    Integer status = 1;
}
