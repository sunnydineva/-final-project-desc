package com.example.finalprojectdesc.controller;

import com.example.finalprojectdesc.conventor.CarConvertor;
import com.example.finalprojectdesc.dto.*;
import com.example.finalprojectdesc.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/cars")
public class CarController {

    CarService carService;

    @Autowired
    public CarController(CarService carService, CarConvertor convertor) {
        this.carService = carService;
    }

    @PostMapping(path = "/save")
    ResponseEntity<CarResponse> add(@Valid @RequestBody CarRequest carRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(carService.add(carRequest));
    }

    @GetMapping(path = "/{id}")
    ResponseEntity<CarResponse> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(carService.findById(id));
    }

    @PutMapping(path = "/price")
    ResponseEntity<CarResponse> updatePrice(@Valid @RequestBody CarUpdatePriceRequest carRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(carService.updatePrice(carRequest));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        carService.deleteById(id);
        return ResponseEntity.ok().build(); // return 200 OK status code

    }
}
