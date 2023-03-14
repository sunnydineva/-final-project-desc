package com.example.finalprojectdesc.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class CarResponse {

    private String id;

    private String make;

    private String model;

    private String carPlate;

    private String color;

    private int seats;

    private BigDecimal pricePerDay;
}
