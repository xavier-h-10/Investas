package com.fundgroup.backend.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fundgroup.backend.constant.ScheduleStatus;
import com.fundgroup.backend.constant.ScheduleType;
import lombok.Data;

import java.util.concurrent.ScheduledFuture;

@Data
public class ScheduleTask {
    String uuid;
    String cron;
    ScheduledFuture<?> scheduledFuture;
    ScheduleType scheduleType;

    public ScheduleTask(){}
    public ScheduleTask(String uuid,String cron,ScheduledFuture<?> scheduledFuture,ScheduleType scheduleType)
    {
        this.uuid=uuid;
        this.cron=cron;
        this.scheduledFuture=scheduledFuture;
        this.scheduleType=scheduleType;
    }
}
