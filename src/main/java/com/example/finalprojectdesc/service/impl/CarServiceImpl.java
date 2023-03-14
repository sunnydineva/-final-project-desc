package com.example.finalprojectdesc.service.impl;

import com.example.finalprojectdesc.conventor.CarConvertor;
import com.example.finalprojectdesc.dto.CarRequest;
import com.example.finalprojectdesc.dto.CarResponse;
import com.example.finalprojectdesc.dto.CarUpdatePriceRequest;
import com.example.finalprojectdesc.entity.Car;
import com.example.finalprojectdesc.exception.RecordNotFoundException;
import com.example.finalprojectdesc.repository.CarRepository;
import com.example.finalprojectdesc.service.CarService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarConvertor carConvertor;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, CarConvertor carConvertor) {
        this.carRepository = carRepository;
        this.carConvertor = carConvertor;
    }

    @Override
    public CarResponse add(CarRequest carRequest) {
        Car car = carConvertor.toCar(carRequest);
        Car carToBeSaved = carRepository.save(car);
        return carConvertor.toResponse(carToBeSaved);
    }

    @Override
    public CarResponse findById(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Car with id %s not found.", id)));
        return carConvertor.toResponse(car);
    }


    //Updates the price per day for a car after checking if the car's plate number and current price match.
    @Override
    @Transactional
    public CarResponse updatePrice(CarUpdatePriceRequest carRequest) {
        Car car = carRepository.findByCarPlate(carRequest.getCarPlate())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Car with %s not found.",
                        carRequest.getCarPlate())));
        if (!(car.getCarPlate().equals(carRequest.getCarPlate())
                && car.getPricePerDay().equals(BigDecimal.valueOf(Double.parseDouble(carRequest.getCurrentPricePerDay()))))) {
            throw new IllegalArgumentException("""
                    Price update unsuccessful.\040
                    Please enter correct car plate, old price and new price""");
        }
        car.setPricePerDay(new BigDecimal(carRequest.getNewPricePerDay()));
        return carConvertor.toResponse(car);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Car> searchedCar = carRepository.findById(id);
        if (searchedCar.isEmpty()) {
            throw new RecordNotFoundException("Car with ID " + id + " not found");
        }
        carRepository.deleteById(id);
    }
}
