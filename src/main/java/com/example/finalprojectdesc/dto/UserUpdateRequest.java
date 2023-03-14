package com.example.finalprojectdesc.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    @NotNull(message = "ID is required")
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(max = 15, message = "First name must be at most 15 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 15, message = "Last name must be at most 15 characters")
    private String lastName;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE, message = "Email should have proper email format")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Phone number is required")
    @Size(min = 6, max = 12, message = "Phone number must be between 6 and 12 digits")
    private String phone;
}
