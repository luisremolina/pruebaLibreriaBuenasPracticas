package com.ceiba.biblioteca.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {
    public static Date getDate() {
        return Calendar.getInstance().getTime();
    }

    public static LocalDate getLocalDate(Calendar calendar) {

        Date date = calendar.getTime();
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static boolean isWeekend(Calendar c) {
        int dia = c.get(Calendar.DAY_OF_WEEK);
        return (dia == Calendar.SUNDAY) || (dia==Calendar.SATURDAY);
    }
}
