package com.chenxb.news;

import com.chenxb.dao.ColumnDao;
import com.chenxb.util.ColumnType;

public class ReloadJob {
	public static void main(String[] args) {
		new Thread() {
			public void run() {
				try {
					new ColumnDao().reInitArticles(ColumnType.JOB);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}.start();
	}
}
