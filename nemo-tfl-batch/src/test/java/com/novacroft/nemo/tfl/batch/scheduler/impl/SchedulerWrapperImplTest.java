package com.novacroft.nemo.tfl.batch.scheduler.impl;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public class SchedulerWrapperImplTest {

    private SchedulerWrapperImpl schedulerWrapper; 
    private SchedulerFactoryBean mockSchedulerFactory;
    private Scheduler mockScheduler;
    private JobDetail mockJobDetail;
    private Trigger mockTrigger;
    
    
    @Before
    public void setUp(){
        schedulerWrapper = new SchedulerWrapperImpl();
        mockSchedulerFactory = mock(SchedulerFactoryBean.class);
        mockJobDetail = mock(JobDetail.class);
        mockTrigger = mock(Trigger.class);
        mockScheduler = mock(Scheduler.class);
        schedulerWrapper.schedulerFactory = mockSchedulerFactory;
    }
    
    @Test
    public void shouldCallRealSchedulerScheduleJob(){
        try{
            when(mockSchedulerFactory.getScheduler()).thenReturn(mockScheduler);
            when(mockScheduler.scheduleJob(mockJobDetail,  mockTrigger)).thenReturn(null);
            schedulerWrapper.scheduleJob(mockJobDetail, mockTrigger);
            verify(mockScheduler, times(1)).scheduleJob(mockJobDetail, mockTrigger);
        }catch(SchedulerException se){
            fail();
        }
    }
}
