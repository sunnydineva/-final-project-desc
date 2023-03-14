package com.example.finalprojectdesc.service.impl;

import com.example.finalprojectdesc.conventor.ReservationConvertor;
import com.example.finalprojectdesc.dto.*;
import com.example.finalprojectdesc.entity.Car;
import com.example.finalprojectdesc.entity.Reservation;
import com.example.finalprojectdesc.exception.RecordNotFoundException;
import com.example.finalprojectdesc.repository.CarRepository;
import com.example.finalprojectdesc.repository.ReservationRepository;
import com.example.finalprojectdesc.service.ReservationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.finalprojectdesc.util.InstantFormatter.instantFromString;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final ReservationConvertor convertor;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, CarRepository carRepository,
                                  ReservationConvertor convertor) {
        this.reservationRepository = reservationRepository;
        this.carRepository = carRepository;
        this.convertor = convertor;
    }

    @Override
    public ReservationResponse add(ReservationRequest reservationRequest) {
        Reservation reservation = convertor.toReservation(reservationRequest);
        Reservation reservationToBeSaved = reservationRepository.save(reservation);
        return convertor.toResponse(reservationToBeSaved);
    }

    @Override
    public ReservationResponse findById(Long id) {
        return convertor.toResponse(reservationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Reservation with ID " + id + " not found")));
    }

    @Override
    public List<ReservationResponse> findByCarCarPlate(String carPlate) {
        return reservationRepository.findByCarCarPlate(carPlate)
                .stream()
                .map(convertor::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponse> findByUserPhone(String userPhone) {
        return reservationRepository.findByUserPhone(userPhone)
                .stream()
                .map(convertor::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        try {
            reservationRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException("Reservation with ID " + id + " not found");
        }
    }

    @Override
    @Transactional
    public ReservationResponse updateCar(ReservationUpdCarRequest reservationRequest) {
        Reservation reservation = reservationRepository.findById(reservationRequest.getId())
                .orElseThrow(() -> new RecordNotFoundException("Reservation with ID "
                        + reservationRequest.getId() + " not found"));
        Car newCar = carRepository.findByCarPlate(reservationRequest.getNewCarPlate())
                .orElseThrow(() -> new RecordNotFoundException("Car with plate "
                        + reservationRequest.getCurrentCarPlate() + " not found"));
        reservation.setCar(newCar);

        return convertor.toResponse(reservation);
    }

    @Override
    @Transactional
    public ReservationResponse updatePeriod(ReservationUpdPeriodRequest reservationRequest) {
        Reservation reservation = reservationRepository.findById(reservationRequest.getId())
                .orElseThrow(() -> new RecordNotFoundException("Reservation with ID "
                        + reservationRequest.getId() + " not found"));
        reservation.setPickUpDate(instantFromString(reservationRequest.getNewPickUpDate()));
        reservation.setReturnDate(instantFromString(reservationRequest.getNewReturnDate()));
        return convertor.toResponse(reservation);
    }

    @Override
    public List<ReservationResponse> getReservationsPeriod(Instant startDate, Instant endDate) {
        return reservationRepository.getReservationsPeriod(startDate, endDate)
                .stream()
                .map(convertor::toResponse)
                .collect(Collectors.toList());
    }
}
