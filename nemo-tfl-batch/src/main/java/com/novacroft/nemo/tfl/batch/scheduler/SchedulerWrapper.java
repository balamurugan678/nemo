package com.novacroft.nemo.tfl.batch.scheduler;

import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

/**
 * A simple Quartz Scheduler wrapper to allow the method call to executed inside a transaction.    
 *
 */
public interface SchedulerWrapper {

    void scheduleJob(JobDetail job, Trigger trigger) throws SchedulerException;
}
