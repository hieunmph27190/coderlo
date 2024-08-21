package com.hieucoder.coderlo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.hieucoder.coderlo.dto.request.UserProfileCreationRequest;
import com.hieucoder.coderlo.dto.response.UserProfileResponse;
import com.hieucoder.coderlo.entity.Role;
import com.hieucoder.coderlo.mapper.UserProfileMapper;
import com.hieucoder.coderlo.repository.httpclient.UserProfileCliient;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hieucoder.coderlo.dto.request.UserCreationRequest;
import com.hieucoder.coderlo.dto.request.UserUpdateRequest;
import com.hieucoder.coderlo.dto.response.UserResponse;
import com.hieucoder.coderlo.entity.User;
import com.hieucoder.coderlo.exception.AppException;
import com.hieucoder.coderlo.exception.ErrorCode;
import com.hieucoder.coderlo.mapper.UserMapper;
import com.hieucoder.coderlo.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Slf4j
public class UserService {

    UserMapper userMapper;
    UserRepository userRepository;
    UserProfileCliient userProfileCliient;
    UserProfileMapper userProfileMapper;

    public UserResponse registration(UserCreationRequest request) {
        User user = userMapper.fromUserCreationRequest(request);
        if (userRepository.existsByUserName(request.getUserName())) throw new AppException(ErrorCode.USER_EXISTED);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder().name("USER").build());
        user.setRoles(roles);
        User userSaved = userRepository.save(user);
        UserProfileCreationRequest userProfileCreationRequest = userMapper.toUserProfileCreationRequest(request);
        userProfileCreationRequest.setUserId(userSaved.getId());
        UserProfileResponse userProfileResponse = userProfileCliient.createUserProfile(userProfileCreationRequest);

        UserResponse userResponse = userMapper.toUserResponse(userSaved);
        userProfileMapper.update(userResponse,userProfileResponse);
        return userResponse;
    }

    public UserResponse createRequest(UserCreationRequest request) {
        User user = userMapper.fromUserCreationRequest(request);
        if (userRepository.existsByUserName(request.getUserName())) throw new AppException(ErrorCode.USER_EXISTED);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userSaved = userRepository.save(user);
        UserProfileCreationRequest userProfileCreationRequest = userMapper.toUserProfileCreationRequest(request);
        userProfileCreationRequest.setUserId(userSaved.getId());
        UserProfileResponse userProfileResponse = userProfileCliient.createUserProfile(userProfileCreationRequest);

        UserResponse userResponse = userMapper.toUserResponse(userSaved);
        userProfileMapper.update(userResponse,userProfileResponse);
        return userResponse;
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public User update(UserUpdateRequest request) {
        User user = new User();
        userMapper.updateUser(user, request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(String s) {
        return userRepository.findById(s);
    }

    @PostAuthorize("returnObject.userName == authentication.name")
    public UserResponse myInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUserName(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    public long count() {
        return userRepository.count();
    }

    public boolean existsById(String s) {
        return userRepository.existsById(s);
    }

    public void deleteById(String s) {
        userRepository.deleteById(s);
    }
}
