package com.chenxb.news;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.chenxb.biz.ArticleBiz;
import com.chenxb.biz.ColumnBiz;
import com.chenxb.dao.ColumnDao;
import com.chenxb.util.ColumnType;
import com.chenxb.util.Constant;
import com.chenxb.util.MysqlTool;
import com.chenxb.util.TableName;
import com.sina.sae.util.SaeUserInfo;

public class Test {
	public static void main(String arg[]) throws Exception {
		new Thread() {
			public void run() {
				try {
					new ColumnDao().initArticles(ColumnType.ACADEMIC);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}.start();
	}

}
