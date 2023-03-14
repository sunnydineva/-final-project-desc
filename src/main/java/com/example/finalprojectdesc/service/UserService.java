package com.example.finalprojectdesc.service;

import com.example.finalprojectdesc.dto.UserUpdatePasswordRequest;
import com.example.finalprojectdesc.dto.UserRequest;
import com.example.finalprojectdesc.dto.UserResponse;
import com.example.finalprojectdesc.dto.UserUpdateRequest;

public interface UserService {
    UserResponse add(UserRequest userRequest);
    UserResponse findById(Long id);
    UserResponse findByEmail(String email);
    UserResponse update(UserUpdateRequest userRequest);
    String updatePassword(UserUpdatePasswordRequest userRequest);
    void deleteById(Long id);
}
