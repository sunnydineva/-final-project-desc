package com.example.finalprojectdesc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationUpdCarRequest {

    @NotNull(message = "Please fill in reservation's id")
    private Long id;

    @NotBlank(message = "Please fill in the current car's plate no")
    private String currentCarPlate;

    @NotBlank(message = "Please fill in the new car's plate no")
    private String newCarPlate;

}
