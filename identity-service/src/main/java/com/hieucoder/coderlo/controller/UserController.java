package com.hieucoder.coderlo.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hieucoder.coderlo.dto.request.UserCreationRequest;
import com.hieucoder.coderlo.dto.request.UserUpdateRequest;
import com.hieucoder.coderlo.dto.respone.ApiResponse;
import com.hieucoder.coderlo.dto.respone.UserResponse;
import com.hieucoder.coderlo.entity.User;
import com.hieucoder.coderlo.mapper.UserMapper;
import com.hieucoder.coderlo.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;
    UserMapper userMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ApiResponse<UserResponse> create(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userMapper.toUserResponse(userService.createRequest(request)))
                .build();
    }

    @PostAuthorize("returnObject.result.userName == authentication.name")
    @GetMapping("/self")
    public ApiResponse<UserResponse> findBySelf() {
        return ApiResponse.<UserResponse>builder().result(userService.myInfo()).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<UserResponse> update(@RequestBody @Valid UserUpdateRequest request, @PathVariable String id) {
        request.setId(id);
        ApiResponse<UserResponse> userApiRespone = new ApiResponse<>();
        userApiRespone.setResult(userMapper.toUserResponse(userService.update(request)));
        return userApiRespone;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public List<User> findAll() {
        return userService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ApiResponse<UserResponse> findById(@PathVariable String id) {
        var user = userService.findById(id).orElseThrow(() -> new RuntimeException("Không tìn thấy id"));
        return ApiResponse.<UserResponse>builder()
                .result(userMapper.toUserResponse(user))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<UserResponse> deleteById(@PathVariable String id) {
        User user = userService.findById(id).orElseThrow(() -> new RuntimeException("Không tìn thấy id"));
        userService.deleteById(user.getId());
        return ApiResponse.<UserResponse>builder()
                .result(userMapper.toUserResponse(user))
                .build();
    }
}
