package com.novacroft.nemo.tfl.batch.scheduler.impl;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.tfl.batch.scheduler.SchedulerWrapper;

/**
 * A simple Quartz Scheduler wrapper to allow the method call to executed inside a transaction.    
 *
 */
@Service("schedulerWrapper")
public class SchedulerWrapperImpl implements SchedulerWrapper {

    protected static final Logger logger = LoggerFactory.getLogger(SchedulerWrapperImpl.class);

    @Autowired
    protected SchedulerFactoryBean schedulerFactory;
    
    @Override
    @Transactional
    public void scheduleJob(JobDetail job, Trigger trigger) throws SchedulerException {
        logger.debug("Scheduling Job using Trigger. JobDetail:" + job + " - Trigger Detail:" + trigger);
        Scheduler scheduler = this.schedulerFactory.getScheduler();
        scheduler.scheduleJob(job, trigger);
    }


}
