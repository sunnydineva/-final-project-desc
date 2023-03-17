package com.example.finalprojectdesc.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
public class InstantFormatter {

    public static Instant instantFromString(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDate.parse(date, formatter).atStartOfDay(zoneId).toInstant();
    }

    public static String instantToString(Instant date){
        ZoneId localZone = ZoneId.systemDefault();
        LocalDate puckUpLocalDate = date.atZone(localZone).toLocalDate();
        return puckUpLocalDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
