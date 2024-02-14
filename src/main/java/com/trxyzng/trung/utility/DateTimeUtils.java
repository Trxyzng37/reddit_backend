package com.trxyzng.trung.utility;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class DateTimeUtils {
    /**
     * return the current date time at UTC+0 base on ISO-8061 format
     * @return string
     */
    public static Instant getCurrentDateTimeInUTC() {
        Instant now = Instant.now().truncatedTo(ChronoUnit.SECONDS);
//        String dateTime = now.toString();
//        dateTime = dateTime.substring(0, dateTime.length() - 1);
        return now;
    }
}
