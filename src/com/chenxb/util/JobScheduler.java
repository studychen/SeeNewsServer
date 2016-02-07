package com.chenxb.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.chenxb.biz.ArticleBiz;
import com.chenxb.biz.ColumnBiz;

public class JobScheduler {

	public static void main(String[] args) throws Exception {
		int[] ids = ColumnBiz.parseColumn(2, 3);
		List<Integer> datas = new LinkedList<Integer>();
		for (int i = 0; i < ids.length; i++) {
			datas.add(ids[i]);
		}

		while (datas.size() > 0) {
			Random r = new Random();
			int id = datas.remove(0);
			System.out.println(ArticleBiz.parseNewsItem(id));
			System.out.println("deal id = " + id);
			Thread.sleep(100 * 1000 + r.nextInt(50 * 1000) + r.nextInt(20 * 1000));

		}

		// JobDetail job =
		// JobBuilder.newJob(TestJob.class).withIdentity("ttt").build();
		// Trigger trigger = TriggerBuilder.newTrigger()
		// .withSchedule(SimpleScheduleBuilder.simpleSchedule()
		// .withIntervalInSeconds(30).repeatForever()).build();
		//
		// SchedulerFactory factory = new StdSchedulerFactory();
		//
		// Scheduler scheduler = factory.getScheduler();
		// scheduler.start();
		// scheduler.scheduleJob(job, trigger);

	}

}
