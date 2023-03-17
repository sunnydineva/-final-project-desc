package com.example.finalprojectdesc.controller;

import com.example.finalprojectdesc.conventor.UserConvertor;
import com.example.finalprojectdesc.dto.UserUpdatePasswordRequest;
import com.example.finalprojectdesc.dto.UserRequest;
import com.example.finalprojectdesc.dto.UserResponse;
import com.example.finalprojectdesc.dto.UserUpdateRequest;
import com.example.finalprojectdesc.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
@Tag(name = "users", description = "Operations related to user management")
public class UserController {

    UserService userService;
    UserConvertor convertor;

    @Autowired
    public UserController(@Qualifier("userServiceImpl") UserService userService, UserConvertor convertor) {
        this.userService = userService;
        this.convertor = convertor;
    }

    @PostMapping(path = "/save")
    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI"), //uses the global defined ApiResponses
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class),
                            examples = {@ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "firstName": "Lilly",
                                      "lastName": "Ivanova",
                                      "email": "lillyivanova@gmail.com",
                                      "phone": "+359 888 888 888"
                                    }""")})),
            @ApiResponse(responseCode = "400", description = "Bad Request: Scenario 1",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(
                                    value = """
                                            Your password should have:
                                            at least one upper case English letter
                                            at least one lower case English letter
                                            at least one digit
                                            at least one special character
                                            minimum eight in length
                                            """))),
            @ApiResponse(responseCode = "400", description = "Bad Request: Scenario 2",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(
                                    value = "Email should have proper email format"))),
            @ApiResponse(responseCode = "400", description = "Bad Request: Scenario 3",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(
                                    value = "Email should have proper email format"))),
            @ApiResponse(responseCode = "400", description = "Bad Request: Scenario 4",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(
                                    value = "SQL constraint violation error"))),
            @ApiResponse(responseCode = "409", description = "Conflict: Scenario 1",
                    content = @Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = """
                                    Cannot delete the record because it is referenced by another records in the database.\040
                                    Please delete the corresponding records first before attempting to delete this record""")})),
            @ApiResponse(responseCode = "409", description = "Conflict: Scenario 2",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(
                                    value = "Duplicates for email not allowed"))),
            @ApiResponse(responseCode = "409", description = "Conflict: Scenario 2",
                    content = @Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "SQL constraint violation error")}))
    })
    public ResponseEntity<UserResponse> add(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    examples = {@ExampleObject(value = """
                            {
                             "firstName": "Lilly",
                             "lastName": "Ivanova",
                             "email": "lillyivanova@gmail.com",
                             "phone": "+359 888 888 888"
                              }""")}
            )) @RequestBody UserRequest userRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.add(userRequest));
    }

    @Transactional
    @PutMapping(path = "/psd")
    @Operation(summary = "Update user password", description = "Update user password with the given email, current password and new password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI"),
            @ApiResponse(responseCode = "200", description = "User password updated successfully",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(value = "Password updated successfully"))),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(value = "User not found"))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(value = "Request parameters are invalid or incomplete, " +
                                    "or if the provided password does not match the user's current password"))),
            @ApiResponse(responseCode = "409", description = "Conflict",
                    content = @Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "Error occur. Try again later")
                            }))
    })
    ResponseEntity<String> updatePassword(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    examples = {@ExampleObject(value = """
                            {
                                "email":"test3@gmail.com",
                                "currentPassword": "Ss1!aaaaa"
                                 "newPassword": "Ss2!aaaaa"
                                 "newPasswordConfirm": "Ss2!aaaaa"
                            }""")}
            ))@RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updatePassword(userUpdatePasswordRequest));
    }

    @Transactional
    @PutMapping(path = "/update")
    @Operation(summary = "Update user information (first, last name, email, phone no) after confirming provided password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI"),
            @ApiResponse(responseCode = "200", description = "User information updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class),
                            examples = {@ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "firstName": "Lilly",
                                      "lastName": "Ivanova",
                                      "email": "lillyivanova@gmail.com",
                                      "phone": "+359 888 888 888"
                                    }""")})),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(value = "User not found"))),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(value = "User not found"))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(value = "Request parameters are invalid or incomplete, or if the provided password does not match the user's current password"))),
            @ApiResponse(responseCode = "409", description = "Conflict",
                    content = @Content(mediaType = "text/plain",
                            examples = {@ExampleObject(value = "Error occur. Try again later")
                            }))
    })
    public ResponseEntity<UserResponse> update(@Valid @RequestBody UserUpdateRequest userRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.update(userRequest));
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Find user by ID", description = "Find a user with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI"),
            @ApiResponse(responseCode = "200", description = "User found successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class),
                            examples = {@ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "firstName": "Lilly",
                                      "lastName": "Ivanova",
                                      "email": "lillyivanova@gmail.com",
                                      "phone": "+359 888 888 888"
                                    }""")})),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(
                                    value = "User not found"))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(
                                    value = "Request parameters are invalid or incomplete, " +
                                            "or if the provided password does not match the user's current password"))),
    })
    ResponseEntity<UserResponse> findById(
            @Parameter(description = "ID of the user to be found")
            @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findById(id));
    }

    @GetMapping(path = "/email/{email}")
    @Operation(summary = "Find user by email", description = "Find a user with the given email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI"),
            @ApiResponse(responseCode = "200", description = "User found successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class),
                            examples = {@ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "firstName": "Lilly",
                                      "lastName": "Ivanova",
                                      "email": "lillyivanova@gmail.com",
                                      "phone": "+359 888 888 888"
                                    }""")})),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    ResponseEntity<UserResponse> getByEmail(
            @Parameter(description = "Email of the user to be found")
            @PathVariable @Valid String email) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findByEmail(email));
    }


    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete user by ID", description = "Deletes an existing user with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "User cannot be deleted because they have associated data"),
            @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI"),}
    )
    ResponseEntity<HttpStatus> delete(
            @Parameter(description = "ID of the user to be deleted")
            @PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


}
