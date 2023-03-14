package com.example.finalprojectdesc.dto;

import com.example.finalprojectdesc.entity.Car;
import com.example.finalprojectdesc.entity.Reservation;
import com.example.finalprojectdesc.entity.User;
import com.example.finalprojectdesc.service.ReservationCalculations;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import static com.example.finalprojectdesc.util.InstantFormatter.instantToString;

@Getter
@Setter
@RequiredArgsConstructor
public class ReservationResponse {
    private Long id;
    private String phone;
    private String carPlate;
    private String pickUpDate;
    private String returnDate;
    private Long rentDuration;
    private BigDecimal reservationPrice;

    public ReservationResponse (Long id, String phone, String carPlate,
                               Instant pickUpDate, Instant returnDate,
                               ReservationCalculations reservationCalculations) {
        this.id = id;
        this.phone = phone;
        this.carPlate = carPlate;

        this.pickUpDate = instantToString(pickUpDate);
        this.returnDate = instantToString(returnDate);

        this.rentDuration = reservationCalculations.getRentDuration(id);
        this.reservationPrice = reservationCalculations.getReservationPrice(id)
                 .setScale(2, RoundingMode.HALF_UP);
    }
}
