package com.example.finalprojectdesc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 15, message = "First name must be at most 15 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 15, message = "Last name must be at most 15 characters")
    private String lastName;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE, message = "Email should have proper email format")
    private String email;

    @Pattern(regexp = "^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*\\s).{8,}$", message = """
            Your password should have:
            at least one upper case English letter
            at least one lower case English letter
            at least one digit
            at least one special character
            minimum eight in length""")
    private String password;

    @NotBlank(message = "Phone number is required")
    @Size(min = 6, max = 12, message = "Phone number must be between 6 and 12 digits")
    private String phone;
}


