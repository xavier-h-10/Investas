package com.fundgroup.backend.constant;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public enum TimeType {
    LAST_ONE_DAY,LAST_ONE_WEEK,LAST_ONE_MONTH,LAST_THREE_MONTHS,LAST_SIX_MONTHS,LAST_ONE_YEAR,
    LAST_TWO_YEARS,LAST_THREE_YEARS,LAST_FIVE_YEARS,FROM_BEGINNING,FROM_THIS_YEAR,

    ONE_DAY_UNTIL,ONE_WEEK_UNTIL,ONE_MONTH_UNTIL;

    public static TimeType fromInteger(int i)
    {
        switch(i)
        {
            case 0:
                return LAST_ONE_DAY;
            case 1:
                return LAST_ONE_WEEK;
            case 2:
                return LAST_ONE_MONTH;
            case 3:
                return LAST_THREE_MONTHS;
            case 4:
                return LAST_SIX_MONTHS;
            case 5:
                return LAST_ONE_YEAR;
            case 6:
                return LAST_TWO_YEARS;
            case 7:
                return LAST_THREE_YEARS;
            case 8:
                return LAST_FIVE_YEARS;
            case 9:
                return FROM_BEGINNING;
            case 10:
                return FROM_THIS_YEAR;
            case 11:
                return ONE_DAY_UNTIL;
            case 12:
                return ONE_WEEK_UNTIL;
            case 13:
                return ONE_MONTH_UNTIL;
            default:
                return null;
        }
    }
}

