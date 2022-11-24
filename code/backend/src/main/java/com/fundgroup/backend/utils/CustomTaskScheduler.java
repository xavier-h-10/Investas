package com.fundgroup.backend.utils;

import com.fundgroup.backend.constant.ScheduleStatus;
import com.fundgroup.backend.constant.ScheduleType;
import com.fundgroup.backend.customAnnotation.LogExecutionTime;
import com.fundgroup.backend.dto.ScheduleTask;
import com.fundgroup.backend.dto.ScheduleTaskInfo;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.springframework.scheduling.support.CronTrigger;

public class CustomTaskScheduler extends ThreadPoolTaskScheduler {

    private final Map<String, ScheduleTask> scheduledTasks=new HashMap<>();

    public ScheduledFuture<?>  schedule(String jobId, ScheduleType scheduleType, String cron, Runnable task, Trigger trigger)
    {
        ScheduledFuture<?> future = super.schedule(task, trigger);
        ScheduleTask scheduleTask=new ScheduleTask(jobId,cron,future,scheduleType);
        scheduledTasks.put(jobId,scheduleTask);
        return future;
    }

    public ScheduledFuture<?> schedule(String jobId, ScheduleType scheduleType, Runnable task, Date startTime)
    {
        ScheduledFuture<?> future = super.schedule(task, startTime);
        ScheduleTask scheduleTask=new ScheduleTask(jobId,null,future,scheduleType);
        scheduledTasks.put(jobId,scheduleTask);
        return future;
    }

    public ScheduleStatus cancelSchedule(String jobId)
    {
        ScheduledFuture<?> future=scheduledTasks.get(jobId).getScheduledFuture();
        if(!scheduledTasks.containsKey(jobId))
        {
            return ScheduleStatus.JOB_NOT_EXIST;
        }
        else{
            boolean isSuccess=future.cancel(true);
            if(!isSuccess)
                return ScheduleStatus.JOB_CANCEL_FAIL;
            return ScheduleStatus.JOB_CANCELED;
        }
    }

    public boolean removeJobInfo(String jobId)
    {
        if(!scheduledTasks.containsKey(jobId))
        {
            return false;
        }
        else
        {
            scheduledTasks.remove(jobId);
        }
        return true;
    }

    public ScheduleStatus getJobStatus(String jobId)
    {
        ScheduledFuture<?> future=scheduledTasks.get(jobId).getScheduledFuture();
        if(!scheduledTasks.containsKey(jobId))
        {
            return ScheduleStatus.JOB_NOT_EXIST;
        }
        //check is canceled first
        else if(future.isCancelled())
        {
            return ScheduleStatus.JOB_CANCELED;
        }
        else if(future.isDone())
        {
            return ScheduleStatus.JOB_DONE;
        }
        long delay=future.getDelay(TimeUnit.SECONDS);
        if(delay>0)
        {
            return ScheduleStatus.JOB_WAIT;
        }
        else
        {
            return ScheduleStatus.JOB_PASS;
        }
    }


    public List<ScheduleTaskInfo> getAllJobStatus()
    {
        List<ScheduleTaskInfo> scheduleTaskInfos=new ArrayList<>();
        for(Map.Entry<String, ScheduleTask> entry : scheduledTasks.entrySet()) {
            String jobId = entry.getKey();
            ScheduleStatus scheduleStatus = getJobStatus(jobId);
            ScheduleTaskInfo scheduleTaskInfo=new ScheduleTaskInfo(jobId,entry.getValue().getCron(),entry.getValue().getScheduleType(),scheduleStatus);
            scheduleTaskInfos.add(scheduleTaskInfo);
        }
        return scheduleTaskInfos;
    }
}
