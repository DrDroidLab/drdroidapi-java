package org.drdroid.api.utils;

import java.time.LocalDateTime;

public class DateTimeFormatter {

    public static String getCurrentFormattedTimeStamp() {
        java.time.format.DateTimeFormatter pattern = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        LocalDateTime localDateTime = LocalDateTime.now();
        return pattern.format(localDateTime);
    }

}
