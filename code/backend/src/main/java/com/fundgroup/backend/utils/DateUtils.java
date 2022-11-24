package com.fundgroup.backend.utils;

import com.fundgroup.backend.constant.TimeType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateUtils {
    public static LocalDate minusDayByTimeType(LocalDate day, TimeType timeType)
    {
        switch(timeType)
        {
            case LAST_ONE_DAY:
                return day.minus(1, ChronoUnit.DAYS);
            case LAST_ONE_WEEK:
                return day.minus(7,ChronoUnit.DAYS);
            case LAST_ONE_MONTH:
                return day.minus(1,ChronoUnit.MONTHS);
            case LAST_THREE_MONTHS:
                return day.minus(3,ChronoUnit.MONTHS);
            case LAST_SIX_MONTHS:
                return day.minus(6,ChronoUnit.MONTHS);
            case LAST_ONE_YEAR:
                return day.minus(1,ChronoUnit.YEARS);
            case LAST_TWO_YEARS:
                return day.minus(2,ChronoUnit.YEARS);
            case LAST_THREE_YEARS:
                return day.minus(3,ChronoUnit.YEARS);
            case LAST_FIVE_YEARS:
                return day.minus(5,ChronoUnit.YEARS);
            case FROM_BEGINNING:
                return LocalDate.parse("1970-01-01");
            case FROM_THIS_YEAR:
                return day.withDayOfYear(1);
        }
        return null;
    }
}
