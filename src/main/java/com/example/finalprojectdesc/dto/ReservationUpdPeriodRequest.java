package com.example.finalprojectdesc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationUpdPeriodRequest {

    @NotNull(message = "Please fill in reservation's id")
    private Long id;

    @NotBlank(message = "Please fill in the current pickup date in dd-mm-yyyy format")
    private String currentPickUpDate;

    @NotBlank(message = "Please fill in the current return date in dd-mm-yyyy format")
    private String currentReturnDate;

    @NotBlank(message = "Please fill in the new pickup date in dd-mm-yyyy format")
    private String newPickUpDate;

    @NotBlank(message = "Please fill in the new return date in dd-mm-yyyy format")
    private String newReturnDate;
}
