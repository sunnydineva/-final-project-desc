package com.example.finalprojectdesc.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarUpdatePriceRequest {

    @NotBlank(message = "Car's plate no is required")
    private String carPlate;

    @NotBlank(message = "Price per day is required")
    private String currentPricePerDay;

    @NotBlank(message = "Price per day is required")
    private String newPricePerDay;
}
