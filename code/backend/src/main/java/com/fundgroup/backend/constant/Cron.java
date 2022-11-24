package com.fundgroup.backend.constant;

import org.springframework.scheduling.support.CronTrigger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cron {
    //weekdays 9:30-11:30 every five minutes
    public static final String TRAN_MORNING_FIVE_MINUTE="0 30/5 9-11 * * 1-5";
    //weekdays 13:00-15:00 every five minutes
    //TODO:modify
    public static final String TRAN_AFTERNOON_FIVE_MINUTE="0 0/5 13-15 * * 1-5";
    //weekdays 9:30-11:30 every minutes
    public static final String TRAN_MORNING_ONE_MINUTE="0 30/1 9-11 * * 1-5";
    //weekdays 13:00-15:00 every minutes
    //TODO:modify
    public static final String TRAN_AFTERNOON_ONE_MINUTE="0 0/1 13-15 * * 1-5";


    public static final String AM0000="0 0 0 * * 1-6";
    public static final String AM0200="0 0 2 * * 1-6";
    public static final String PM1600="0 0 16 * * 1-5";
    public static final String PM1630="0 30 16 * * 1-5";
    public static final String PM1700="0 0 17 * * 1-5";
    public static final String PM1730="0 30 17 * * 1-5";
    public static final String PM1900="0 0 19 * * 1-5";
    public static final String PM2100="0 0 19 * * 1-5";
    public static final String AM0500="0 0 5 * * 1-5";

    public static final String test="0 0/5 * * * 0-6";

    public static final List<CronTrigger> TRAN_ONE_MINUTE= Arrays.asList(new CronTrigger(TRAN_MORNING_ONE_MINUTE),new CronTrigger(TRAN_AFTERNOON_ONE_MINUTE));
    public static final List<CronTrigger> TRAN_FIVE_MINUTE=Arrays.asList(new CronTrigger(TRAN_MORNING_FIVE_MINUTE),new CronTrigger(TRAN_AFTERNOON_FIVE_MINUTE));
    public static final List<CronTrigger> TEST=Arrays.asList(new CronTrigger(test));
    public static final List<CronTrigger> CRON_PM1700=Arrays.asList(new CronTrigger(PM1700));
    public static final List<CronTrigger> CRON_PM1600=Arrays.asList(new CronTrigger(PM1600));
}
