package com.example.finalprojectdesc.controller;

import com.example.finalprojectdesc.dto.*;
import com.example.finalprojectdesc.service.ReservationCalculations;
import com.example.finalprojectdesc.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import static com.example.finalprojectdesc.util.InstantFormatter.instantFromString;

@RestController
@RequestMapping(path = "/reservations")
public class ReservationController {

    ReservationService reservationService;
    ReservationCalculations reservationCalculations;

    @Autowired
    public ReservationController(ReservationService reservationService, ReservationCalculations reservationCalculations) {
        this.reservationService = reservationService;
        this.reservationCalculations = reservationCalculations;
    }

    @PostMapping(path = "/save")
    ResponseEntity<ReservationResponse> add(@Valid @RequestBody ReservationRequest reservationRequest) {
        // Validate pickUpDate is in the future
        if (instantFromString(reservationRequest.getPickUpDate()).isBefore(Instant.now())
                || instantFromString(reservationRequest.getReturnDate()).isBefore(Instant.now())
                || instantFromString(reservationRequest.getReturnDate())
                .isBefore(instantFromString(reservationRequest.getPickUpDate()))) {
            throw new IllegalArgumentException("""
                    Pick-up & return dates must be in the future
                    Return day should be after pick-up day""");
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reservationService.add(reservationRequest));
    }

    @GetMapping(path = "/{id}")
    ResponseEntity<ReservationResponse> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(reservationService.findById(id));
    }

    @GetMapping(path = "/user/{phone}")
    ResponseEntity<List<ReservationResponse>> findByUser(@PathVariable String phone) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(reservationService.findByUserPhone(phone));
    }

    @GetMapping(path = "/car/{carPlate}")
    ResponseEntity<List<ReservationResponse>> findByCar(@PathVariable String carPlate) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(reservationService.findByCarCarPlate(carPlate));
    }

    @GetMapping(path = "/period")
    ResponseEntity<List<ReservationResponse>> findByPeriod(@Valid @RequestBody ReservationPeriodRequest reservationRequest) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(reservationService.getReservationsPeriod(instantFromString(reservationRequest.getStartDate()),
                        instantFromString(reservationRequest.getEndDate())));
    }

    @PutMapping(path = "/update/car")
    ResponseEntity<ReservationResponse> update(@Valid @RequestBody ReservationUpdCarRequest reservationRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reservationService.updateCar(reservationRequest));
    }

    @PutMapping(path = "/update/period")
    ResponseEntity<ReservationResponse> update(@Valid @RequestBody ReservationUpdPeriodRequest reservationRequest) {
        if (instantFromString(reservationRequest.getNewPickUpDate()).isBefore(Instant.now())
                || instantFromString(reservationRequest.getNewReturnDate()).isBefore(Instant.now())
                || instantFromString(reservationRequest.getNewReturnDate())
                .isBefore(instantFromString(reservationRequest.getNewPickUpDate()))) {
            throw new IllegalArgumentException("""
                    Pick-up & return dates must be in the future
                    Return day should be after pick-up day""");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reservationService.updatePeriod(reservationRequest));
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.ok().build(); // return 200 OK status code
    }
}
