package com.example.finalprojectdesc.controller;

import com.example.finalprojectdesc.conventor.UserConvertor;
import com.example.finalprojectdesc.dto.UserUpdatePasswordRequest;
import com.example.finalprojectdesc.dto.UserRequest;
import com.example.finalprojectdesc.dto.UserResponse;
import com.example.finalprojectdesc.dto.UserUpdateRequest;
import com.example.finalprojectdesc.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    UserService userService;
    UserConvertor convertor;
    @Autowired
    public UserController(@Qualifier("userServiceImpl") UserService userService, UserConvertor convertor) {
        this.userService = userService;
        this.convertor = convertor;
    }

    @PostMapping(path = "/save")
    ResponseEntity<UserResponse> add(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.add(userRequest));
    }


/* if I make the security
    @PostMapping(path = "/login") //????????????????????????????????
    ResponseEntity<UserResponse> login(@Valid @RequestBody UserRequest loginRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.login(loginRequest));
    }

 */

    @Transactional
    @PutMapping(path = "/psd")
    ResponseEntity<String> updatePassword(@Valid @RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updatePassword(userUpdatePasswordRequest));
    }

    @Transactional
    @PutMapping(path = "/update")
    ResponseEntity<UserResponse> update(@Valid @RequestBody UserUpdateRequest userRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.update(userRequest));
    }

    @GetMapping(path = "/{id}")
    ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(userService.findById(id));
    }

    @GetMapping(path = "/email/{email}")
    ResponseEntity<UserResponse> getByEmail(@PathVariable @Valid String email) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(userService.findByEmail(email));
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<HttpStatus> delete(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity
                .ok().build();
    }
}
