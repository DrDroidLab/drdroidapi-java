package io.drdroid.api.utils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class DateTimeFormatter {

    public static String getCurrentFormattedTimeStamp() {
        OffsetDateTime now = OffsetDateTime.now( ZoneOffset.UTC );
        return java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").format(now) + 'Z';
    }

}
