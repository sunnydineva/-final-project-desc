package com.example.finalprojectdesc.repository;

import com.example.finalprojectdesc.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByCarCarPlate(String carPlate); //refers to findByCarPlate form carRepository

    List<Reservation> findByUserPhone(String user);  //refers to findByPhone form userRepository

    @Query(nativeQuery = true, value = "SELECT * FROM reservations WHERE pickUpDate BETWEEN ?1 AND ?2 OR returnDate BETWEEN ?1 AND ?2")
    List<Reservation> getReservationsDateRange2(Instant startDate, Instant endDate);

    @Query(value = "SELECT r FROM Reservation r WHERE r.pickUpDate BETWEEN :startDate " +
            "AND :endDate OR r.returnDate BETWEEN :startDate AND :endDate")
    List<Reservation> getReservationsPeriod(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);

    @Query(nativeQuery = true, value = "SELECT * FROM reservations")
    List<Reservation> getAllReservations();

}
