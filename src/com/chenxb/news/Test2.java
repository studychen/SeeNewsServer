package com.chenxb.news;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

import com.chenxb.biz.ColumnBiz;
import com.chenxb.dao.ColumnDao;
import com.chenxb.util.ColumnType;
import com.chenxb.util.MysqlTool;
import com.chenxb.util.TableName;

public class Test2 {
	public static void main(String arg[]) throws Exception {
		new Thread() {
			public void run() {
				try {
					ColumnDao.justParseArticles(ColumnType.BACHELOR);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}.start();

		new Thread() {
			public void run() {
				try {
					ColumnDao.justParseArticles(ColumnType.MASTER);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}.start();

		new Thread() {
			public void run() {
				try {
					ColumnDao.justParseArticles(ColumnType.JOB);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}.start();

		new Thread() {
			public void run() {
				try {
					ColumnDao.justParseArticles(ColumnType.ACADEMIC);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}.start();

		new Thread() {
			public void run() {
				try {
					ColumnDao.justParseArticles(ColumnType.LATEST);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}.start();

		new Thread() {
			public void run() {
				try {
					ColumnDao.justParseArticles(ColumnType.NOTIFIC);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}.start();

		// System.out.println(new
		// ColumnDao().isTableEmpty(ColumnType.BACHELOR));
		// Connection connection = MysqlTool.getConnection();
		// String tableName = TableName.getTableByType(ColumnType.LATEST);
		//
		// int[] ids = ColumnBiz.parseColumn(ColumnType.LATEST, 1);
		//
		// System.out.println(ArrayUtils.indexOf(ids, ids[0]));
		// System.out.println(ArrayUtils.indexOf(ids, 6531));
		// System.out.println(ids[0]);
		// Arrays.binarySearch(ids, 3);
		// System.out.println(ArrayUtils.contains(ids, 7948));
		// System.out.println(ArrayUtils.contains(ids, 4));
		// System.out.println(ArrayUtils.contains(ids, 7945));
		// // Arrays.asList(ids);
		// // System.out.println(list);

	}

}
