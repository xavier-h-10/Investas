package com.fundgroup.backend.dto;

import com.fundgroup.backend.constant.ScheduleType;
import lombok.Data;

import java.util.List;

@Data
public class SpiderScheduleRequest {
    String spider;
    List<String> cronStrings;
    public SpiderScheduleRequest(){}
}
