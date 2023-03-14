package com.example.finalprojectdesc.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRequest {

    @NotBlank(message = "Please fill in user's phone")
    private String phone;

    @NotBlank(message = "Please fill in car's plate no")
    private String carPlate;

    @NotBlank(message = "Please fill in pickup date in dd-mm-yyyy format")
    private String pickUpDate;

    @NotBlank(message = "Please fill in return date in dd-mm-yyyy format")
    private String returnDate;
}
