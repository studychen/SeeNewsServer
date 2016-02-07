package com.chenxb.news;

import com.chenxb.dao.ColumnDao;
import com.chenxb.util.ColumnType;

public class ReloadAcademic {
	public static void main(String[] args) {
		new Thread() {
			public void run() {
				try {
					new ColumnDao().reInitArticles(ColumnType.ACADEMIC);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}.start();
	}
}
