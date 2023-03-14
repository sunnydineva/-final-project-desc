package com.example.finalprojectdesc.repository;

import com.example.finalprojectdesc.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByCarPlate(String carPlate);
}
