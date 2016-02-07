package com.chenxb.test;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class JobScheduler {
	public static void main(String[] args) throws Exception {
		JobDetail job = JobBuilder.newJob(TestJob.class).withIdentity("ttt").build();
		Trigger trigger = TriggerBuilder.newTrigger()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(30).repeatForever()).build();

		SchedulerFactory factory = new StdSchedulerFactory();

		Scheduler scheduler = factory.getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
	}

}

// class MyTrigger extend Trig
