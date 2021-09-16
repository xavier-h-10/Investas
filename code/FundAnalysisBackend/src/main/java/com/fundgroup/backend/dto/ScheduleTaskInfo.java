package com.fundgroup.backend.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fundgroup.backend.constant.ScheduleStatus;
import com.fundgroup.backend.constant.ScheduleType;
import lombok.Data;

import java.util.concurrent.ScheduledFuture;

@Data
public class ScheduleTaskInfo {
    @JSONField(name="uuid")
    String uuid;
    @JSONField(name="cron")
    String cron;
    @JSONField(name="scheduleType")
    ScheduleType scheduleType;
    @JSONField(name="scheduleStatus")
    ScheduleStatus scheduleStatus;

    public ScheduleTaskInfo(){}
    public ScheduleTaskInfo(String uuid,String cron,ScheduleType scheduleType,ScheduleStatus scheduleStatus)
    {
        this.uuid=uuid;
        this.cron=cron;
        this.scheduleType=scheduleType;
        this.scheduleStatus=scheduleStatus;
    }
}
