package com.chenxb.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TimeTool {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");

	private static final Random random = new Random();
	private static final int WAIT_TIME = 60 * 1000;// 毫秒

	/**
	 * 
	 * @return 格式化，得到当前的日期
	 */
	public static String getCurrentTime() {
		return dateFormat.format(new Date());
	}

	/**
	 * 等待一段时间，防止对被爬虫的网站负载太大
	 */
	public static void sleepSomeTime() {
		try {
			Thread.sleep(random.nextInt(WAIT_TIME) + WAIT_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
