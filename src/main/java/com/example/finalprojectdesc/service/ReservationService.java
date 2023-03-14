package com.example.finalprojectdesc.service;
import com.example.finalprojectdesc.dto.*;
import java.time.Instant;
import java.util.List;

public interface ReservationService {
    ReservationResponse add(ReservationRequest reservationRequest);
    ReservationResponse findById(Long id);
    List<ReservationResponse> findByCarCarPlate(String carPlate);
    List<ReservationResponse> findByUserPhone(String userPhone);
    void deleteById(Long id);
    ReservationResponse updateCar(ReservationUpdCarRequest reservationRequest);

    ReservationResponse updatePeriod(ReservationUpdPeriodRequest reservationRequest);
    List<ReservationResponse> getReservationsPeriod(Instant startDate, Instant endDate);
}
