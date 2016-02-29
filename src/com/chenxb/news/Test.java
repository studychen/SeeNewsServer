package com.chenxb.news;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.chenxb.biz.ArticleBiz;
import com.chenxb.biz.ColumnBiz;
import com.chenxb.dao.ColumnDao;
import com.chenxb.model.ArticleItem;
import com.chenxb.model.SimpleArticleItem;
import com.chenxb.util.ColumnType;
import com.chenxb.util.Constant;
import com.chenxb.util.MysqlTool;
import com.chenxb.util.TableName;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sina.sae.util.SaeUserInfo;

public class Test {
	private String x;

	public static void main(String arg[]) throws Exception {
			test(7937);
	}

	public static void test(int id) {
		Random rand = new Random(id);
		System.out.println(rand.nextInt(965));
	}

	public static final boolean isCloud = false;
	// 新浪云 ip，外网使用
	public static final String saeIP = "http://javanews.applinzi.com/";
	// 本地局域网 ip，测试使用
	public static final String localIP = "http://192.168.199.133/";

	public static String columnUrl() {
		String suffix = "columnWithSql?column=%d&offset=%d";
		if (isCloud)
			return saeIP + suffix;
		else
			return localIP + suffix;
	}

	public static String articleUrl() {
		String suffix = "articleWithSql?column=%d&id=%d";
		if (isCloud)
			return saeIP + suffix;
		else
			return localIP + suffix;
	}

}
