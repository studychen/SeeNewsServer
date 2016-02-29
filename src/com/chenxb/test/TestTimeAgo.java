package com.chenxb.test;

import com.chenxb.util.GetTimeAgo;

public class TestTimeAgo {
	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		System.out.println(time);

		System.out.println(time / 1000 < 1000000000000L);

		try {
			Thread.sleep(1000 * 280);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(GetTimeAgo.getTimeAgo(time / 1000));

	}

}
