package com.example.finalprojectdesc.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long id;

    @Column(nullable = false)
    @Size(min = 3, max = 15)
    private String make;

    @Column(nullable = false)
    @Size(min = 1, max = 15)
    private String model;

    @Column(nullable = false, name = "car_plate")
    @Size(max = 30)
    private String carPlate;

    @Column(nullable = false)
    @Size(min = 2, max = 12)
    private String color;

    @Column(nullable = false)
    private Integer seats;

    @Column(nullable = false, name = "price_per_day")
    private BigDecimal pricePerDay;

    @OneToMany(mappedBy = "car")
    @JsonBackReference
    private Set<Reservation> reservations;
}
