package com.pny.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * LocalDate, LocalDateTime, Date转换
 *
 * @author pmyun
 */
public final class DateWrapper {

    private static final ZoneId defaultZoneId = ZoneId.systemDefault();

    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(defaultZoneId).toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(defaultZoneId).toLocalDateTime();
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(defaultZoneId).toInstant());
    }

    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    }

}
