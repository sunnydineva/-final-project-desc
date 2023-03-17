package com.example.finalprojectdesc.conventor;

import com.example.finalprojectdesc.dto.CarRequest;
import com.example.finalprojectdesc.dto.CarResponse;
import com.example.finalprojectdesc.entity.Car;
import org.springframework.stereotype.Component;

@Component
public class CarConvertor {

    public Car toCar(CarRequest carRequest) {
        return Car.builder()
                .make(carRequest.getMake())
                .model(carRequest.getModel())
                .carPlate(carRequest.getCarPlate())
                .seats(carRequest.getSeats())
                .color(carRequest.getColor())
                .pricePerDay(carRequest.getPricePerDay())
                .build();
    }

    public CarResponse toResponse(Car car) {
        return CarResponse.builder()
                .id(Long.toString(car.getId()))
                .make(car.getMake())
                .model(car.getModel())
                .carPlate(car.getCarPlate())
                .seats(car.getSeats())
                .color(car.getColor())
                .pricePerDay(car.getPricePerDay())
                .build();
    }
}
