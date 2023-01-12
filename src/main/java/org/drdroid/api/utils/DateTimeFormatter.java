package org.drdroid.api.utils;

import java.time.LocalDate;

public class DateTimeFormatter {

    public static String getCurrentFormattedTimeStamp() {
        java.time.format.DateTimeFormatter pattern = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        LocalDate localDate = LocalDate.now();
        return pattern.format(localDate);
    }

}
