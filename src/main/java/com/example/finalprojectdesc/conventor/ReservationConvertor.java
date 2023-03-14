package com.example.finalprojectdesc.conventor;

import com.example.finalprojectdesc.dto.ReservationRequest;
import com.example.finalprojectdesc.dto.ReservationResponse;
import com.example.finalprojectdesc.entity.Car;
import com.example.finalprojectdesc.entity.Reservation;
import com.example.finalprojectdesc.entity.User;
import com.example.finalprojectdesc.exception.RecordNotFoundException;
import com.example.finalprojectdesc.repository.CarRepository;
import com.example.finalprojectdesc.repository.ReservationRepository;
import com.example.finalprojectdesc.repository.UserRepository;
import com.example.finalprojectdesc.service.ReservationCalculations;
import org.springframework.stereotype.Component;
import static com.example.finalprojectdesc.util.InstantFormatter.instantFromString;

@Component
public class ReservationConvertor {


    ReservationRepository reservationRepository;
    ReservationCalculations reservationCalculations;
    UserRepository userRepository;
    CarRepository carRepository;

    public ReservationConvertor(ReservationRepository reservationRepository,
                                ReservationCalculations reservationCalculations,
                                UserRepository userRepository,
                                CarRepository carRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationCalculations = reservationCalculations;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    public ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getUser().getPhone(),
                reservation.getCar().getCarPlate(),
                reservation.getPickUpDate(),
                reservation.getReturnDate(),
                reservationCalculations);
    }

    public Reservation toReservation(ReservationRequest reservationRequest) {
        User user = userRepository.findByPhone(reservationRequest.getPhone())
                .orElseThrow(() -> new RecordNotFoundException("User not found. Reservation failed"));
        Car car = carRepository.findByCarPlate(reservationRequest.getCarPlate())
                .orElseThrow(() -> new RecordNotFoundException("Car not found. Reservation failed"));

        return Reservation.builder()
                .user(user)
                .car(car)
                .pickUpDate(instantFromString(reservationRequest.getPickUpDate()))
                .returnDate(instantFromString(reservationRequest.getReturnDate()))
                .build();
    }
}
