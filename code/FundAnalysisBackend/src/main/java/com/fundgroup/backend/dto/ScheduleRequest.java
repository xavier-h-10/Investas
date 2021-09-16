package com.fundgroup.backend.dto;

import com.fundgroup.backend.constant.ScheduleType;
import lombok.Data;

import java.util.List;

@Data
public class ScheduleRequest {
    ScheduleType scheduleType;
    List<Object> arguments;
    List<String> cronStrings;
    public ScheduleRequest(){}
}
