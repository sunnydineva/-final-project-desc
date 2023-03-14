package com.example.finalprojectdesc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
public class CarRequest {

    @NotBlank(message = "Car's make is required")
    private String make;
    @NotBlank(message = "Car's model is required")
    private String model;
    @NotBlank(message = "Car's plate no is required")
    private String carPlate;
    @NotBlank(message = "Car's color is required")
    private String color;
    @NotNull(message = "Seats no is required")
    private int seats;
    @NotNull(message = "Price per day is required")
    private BigDecimal pricePerDay;
}
