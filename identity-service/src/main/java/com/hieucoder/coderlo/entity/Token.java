package com.hieucoder.coderlo.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Token {
    @Id
    String id;

    String userName;

    @Column(columnDefinition = "TEXT")
    String token;

    Date expiryTime;

    Integer status;
}
