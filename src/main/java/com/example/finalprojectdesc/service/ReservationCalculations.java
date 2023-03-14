package com.example.finalprojectdesc.service;

import java.math.BigDecimal;

public interface ReservationCalculations {

    Long getRentDuration(Long id);
    BigDecimal getReservationPrice(Long id);
}
