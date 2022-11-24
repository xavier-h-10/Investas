package com.fundgroup.backend.config;

import com.fundgroup.backend.controller.schedule.CustomSchedule;
import com.fundgroup.backend.controller.schedule.PipelineSchedule;
import com.fundgroup.backend.utils.CustomTaskScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan(basePackages = "com.fundgroup.backend")
public class SchedulerConfig {
    @Bean(name = "customTaskScheduler")
    public CustomTaskScheduler customTaskScheduler(){
        CustomTaskScheduler customTaskScheduler= new CustomTaskScheduler();
        customTaskScheduler.setPoolSize(20);
        customTaskScheduler.setThreadNamePrefix("CustomTaskScheduler");
        return customTaskScheduler;
    }

    @Bean(name = "pipelineTaskScheduler")
    public CustomTaskScheduler pipelineTaskScheduler(){
        CustomTaskScheduler customTaskScheduler= new CustomTaskScheduler();
        customTaskScheduler.setPoolSize(1);
        customTaskScheduler.setThreadNamePrefix("PipelineTaskScheduler");
        return customTaskScheduler;
    }
}
