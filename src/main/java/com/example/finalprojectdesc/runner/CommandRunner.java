package com.example.finalprojectdesc.runner;

import com.example.finalprojectdesc.entity.Car;
import com.example.finalprojectdesc.entity.Reservation;
import com.example.finalprojectdesc.entity.User;
import com.example.finalprojectdesc.repository.CarRepository;
import com.example.finalprojectdesc.repository.ReservationRepository;
import com.example.finalprojectdesc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static com.example.finalprojectdesc.util.InstantFormatter.instantFromString;


@Component
public class CommandRunner implements CommandLineRunner {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    CarRepository carRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReservationRepository reservationRepository;

    @Override
    public void run(String... args)  {

        Car car1 = Car.builder()
                .make("Mercedes")
                .model("Benz")
                .seats(4)
                .carPlate("B1111HT")
                .color("white")
                .pricePerDay(new BigDecimal("100.00"))
                .build();

        Car car2 = Car.builder()
                .make("BMW")
                .model("X5")
                .seats(4)
                .carPlate("B2222HT")
                .color("black")
                .pricePerDay(new BigDecimal("90.00"))
                .build();
        carRepository.save(car1);
        carRepository.save(car2);
        System.out.println(car1.getCarPlate());
        System.out.println(car2.getCarPlate());

        User user1 = User.builder()
                .firstName("Sunny")
                .lastName("Dineva")
                .password(bCryptPasswordEncoder.encode("1"))
                .phone("0899044806")
                .email("sd@gmail.com")
                .build();

        User user2 = User.builder()
                .firstName("Bunny")
                .lastName("Bobeva")
                .password(bCryptPasswordEncoder.encode("2"))
                .phone("0899044888")
                .email("bb@gmail.com")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        System.out.println(user1.getEmail());
        System.out.println(user2.getEmail());

        Reservation reservation1 = Reservation.builder()
                .car(car1)
                .user(user1)
                .pickUpDate(instantFromString("20-06-2024"))
                .returnDate(instantFromString("25-06-2024"))
                .build();
        reservationRepository.save(reservation1);

        Optional<Reservation> savedReservation = reservationRepository.findById(reservation1.getId());
        if (savedReservation.isPresent()) {
            System.out.println("Reservation saved successfully!");
        } else {
            System.out.println("Failed to save reservation!");
        }

        Long days = ChronoUnit.DAYS
                .between(reservation1.getPickUpDate().atZone(ZoneId.systemDefault()),
                reservation1.getReturnDate().atZone(ZoneId.systemDefault()));
        System.out.println(days);

    }
}
