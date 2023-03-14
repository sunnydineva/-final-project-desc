package com.example.finalprojectdesc.service.impl;

import com.example.finalprojectdesc.entity.Reservation;
import com.example.finalprojectdesc.exception.RecordNotFoundException;
import com.example.finalprojectdesc.repository.ReservationRepository;
import com.example.finalprojectdesc.service.ReservationCalculations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Component
public class ReservationCalculationsImpl implements ReservationCalculations {

    ReservationRepository reservationRepository;


    @Autowired
    public ReservationCalculationsImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Long getRentDuration(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RecordNotFoundException("Reservation with id " + reservationId + " not found"));
        long days = ChronoUnit.DAYS
                .between(reservation.getPickUpDate().atZone(ZoneId.systemDefault()),
                        reservation.getReturnDate().atZone(ZoneId.systemDefault()));
        if(days == 0){ // the price is for min 1 day even if the car is returned the same day
            days = 1L;
        }
        return days;
    }

    @Override
    public BigDecimal getReservationPrice(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RecordNotFoundException("Reservation with id " + reservationId + " not found"));
        return reservation.getCar().getPricePerDay()
                .multiply(new BigDecimal(getRentDuration(reservationId)));
    }
}
