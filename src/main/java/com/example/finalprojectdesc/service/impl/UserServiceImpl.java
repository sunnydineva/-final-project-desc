package com.example.finalprojectdesc.service.impl;

import com.example.finalprojectdesc.conventor.UserConvertor;
import com.example.finalprojectdesc.dto.UserUpdatePasswordRequest;
import com.example.finalprojectdesc.dto.UserRequest;
import com.example.finalprojectdesc.dto.UserResponse;
import com.example.finalprojectdesc.dto.UserUpdateRequest;
import com.example.finalprojectdesc.entity.User;
import com.example.finalprojectdesc.exception.RecordNotFoundException;
import com.example.finalprojectdesc.repository.UserRepository;
import com.example.finalprojectdesc.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConvertor userConvertor;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    String errMsgBadCredentials = "Invalid email or password." +
            " Please check your credentials and try again.";

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConvertor userConvertor,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userConvertor = userConvertor;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserResponse add(UserRequest userRequest) {
        User user = userConvertor.toUser(userRequest);
        User userToBeSaved = userRepository.save(user);
        return userConvertor.toResponse(userToBeSaved);
    }

    @Override
    public UserResponse findById(Long id) {
        return userConvertor.toResponse(userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("User with id %s not found.", id))));
    }

    @Override
    public UserResponse findByEmail(String email) {
        return userConvertor.toResponse(userRepository.findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException(String.format("User with email %s not found.", email))));
    }

    //Update of firstName, lastName, email, phone
    @Override
    @Transactional
    public UserResponse update(UserUpdateRequest userRequest) {
        User user = userRepository.findById(userRequest.getId())
                .orElseThrow(() -> new RecordNotFoundException(errMsgBadCredentials));
        // the password in UserRequest is for confirmation
        if (bCryptPasswordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setEmail(userRequest.getEmail());
            user.setPhone(userRequest.getPhone());
        } else {
            throw new RecordNotFoundException(errMsgBadCredentials);
        }
        return userConvertor.toResponse(user);
    }


    //Updates the user's password after verifying the correctness of the current password.
    @Override
    @Transactional
    public String updatePassword(UserUpdatePasswordRequest userRequest) {
        User user = userRepository.findByEmail(userRequest.getEmail())
                .orElseThrow(() -> new RecordNotFoundException(errMsgBadCredentials));
        if (bCryptPasswordEncoder.matches(userRequest.getCurrentPassword(), user.getPassword())
        && userRequest.getNewPassword().equals(userRequest.getNewPasswordConfirm())
        ) {
            user.setPassword(bCryptPasswordEncoder.encode(userRequest.getNewPassword()));
        } else {
            throw new RecordNotFoundException(errMsgBadCredentials);
        }
        return "Password successfully changed";
    }

    @Override
    public void deleteById(Long id) {
        Optional<User> searchedCar = userRepository.findById(id);
        if (searchedCar.isEmpty()) {
            throw new RecordNotFoundException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
    }
}

