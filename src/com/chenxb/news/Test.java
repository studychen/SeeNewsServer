package com.chenxb.news;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
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
	public static void main(String arg[]) throws Exception {
		int id =7000;
		ArticleItem article = ArticleBiz.parseNewsItem(id);
//		String jsonData="[{\"id\":7947,\"title\":\"电子工程学院2016上学期本科课程表\",\"publishDate\":\"2016-02-02\",\"readTimes\":292},{\"id\":7936,\"title\":\"本科教务系统成绩录入操作以及导出名单、课表查询说明\",\"publishDate\":\"2016-01-20\",\"readTimes\":481},{\"id\":7922,\"title\":\"关于电院青年教师讲课竞赛（实验类）安排的通知\",\"publishDate\":\"2016-01-08\",\"readTimes\":533},{\"id\":7914,\"title\":\"关于《微波工程设计和仿真软件HFSS应用》讲座开课的通知\",\"publishDate\":\"2016-01-05\",\"readTimes\":446},{\"id\":7906,\"title\":\"关于统计2015年发表教学论文和出版教材的通知\",\"publishDate\":\"2015-12-30\",\"readTimes\":275},{\"id\":7872,\"title\":\"关于批准2016年国家级、省级大学生创新创业训练计划培育项目立项及组织开题审核工作的通知\",\"publishDate\":\"2015-12-17\",\"readTimes\":422},{\"id\":7860,\"title\":\"西安电子科技大学电子工程学院关于学校第十一届青年教师讲课竞赛院级选拔的通知\",\"publishDate\":\"2015-12-11\",\"readTimes\":858},{\"id\":7859,\"title\":\"电子工程学院关于组织2015年优质教学质量奖评选工作的通知\",\"publishDate\":\"2015-12-10\",\"readTimes\":545},{\"id\":7843,\"title\":\"电子工程学院关于对2012级本科毕业设计后续工作安排的通知\",\"publishDate\":\"2015-12-04\",\"readTimes\":2249},{\"id\":7837,\"title\":\"关于举办“2016 年（第9 届）中国大学生计算机设计大赛”的通知\",\"publishDate\":\"2015-12-02\",\"readTimes\":534},{\"id\":7831,\"title\":\"关于组织开展2015年优质教学质量奖评选工作的通知\",\"publishDate\":\"2015-11-30\",\"readTimes\":365},{\"id\":7821,\"title\":\"关于印发《西安电子科技大学优质教学质量奖评选实施细则（试行）》的通知\",\"publishDate\":\"2015-11-23\",\"readTimes\":443},{\"id\":7820,\"title\":\"关于举办2015年在线开放课程建设培训会的通知\",\"publishDate\":\"2015-11-23\",\"readTimes\":280},{\"id\":7819,\"title\":\"关于举办2016年“微软创新杯”陕西高校大学生创新创业大赛的通知\",\"publishDate\":\"2015-11-21\",\"readTimes\":544},{\"id\":7818,\"title\":\"2016年度国家级省级大学生创新创业训练计划拟培育项目立项公示\",\"publishDate\":\"2015-11-21\",\"readTimes\":655}]";
//		 Gson gson = new GsonBuilder().create();
//
//         Type type = new TypeToken<List<SimpleArticleItem>>() {
//         }.getType();
//         List<SimpleArticleItem> articles = gson.fromJson(jsonData, type);
//         
//         System.out.println(articles);
//         System.out.println(articles.size());
//       
	}

}
