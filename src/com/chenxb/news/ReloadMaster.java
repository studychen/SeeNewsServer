package com.chenxb.news;

import com.chenxb.dao.ColumnDao;
import com.chenxb.util.ColumnType;

public class ReloadMaster {
	public static void main(String[] args) {
		new Thread() {
			public void run() {
				try {
					new ColumnDao().reInitArticles(ColumnType.MASTER);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}.start();
	}
}
