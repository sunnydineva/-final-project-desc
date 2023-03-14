package com.example.finalprojectdesc.service;

import com.example.finalprojectdesc.dto.CarRequest;
import com.example.finalprojectdesc.dto.CarResponse;
import com.example.finalprojectdesc.dto.CarUpdatePriceRequest;

public interface CarService {
        CarResponse add(CarRequest carRequest);
        CarResponse findById(Long id);
        CarResponse updatePrice(CarUpdatePriceRequest carRequest);
        void deleteById(Long id);

}
