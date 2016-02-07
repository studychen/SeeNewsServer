package com.chenxb.test;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.chenxb.biz.ArticleBiz;
import com.chenxb.util.Constant;
import com.chenxb.util.UrlTool;

public class TestJob implements Job {

	private int articleId;

	public TestJob(int articleId) {
		this.articleId = articleId;
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		System.out.println("========== articleId " + articleId + " ==========");
		System.out.println("TestJob running");
	}

	public static void main(String[] args) throws IOException {
		// 根据后缀的数字，拼接新闻 url
		Document doc = Jsoup.connect(Constant.SEE_URL).timeout(10000).get();
		// 去掉jsoup对html字符串加的"\n"，方便json字符串返回
		doc.outputSettings().prettyPrint(false);

		Elements eles = doc.getElementsByClass("rotaion_list");
		System.out.println(eles.get(0));
		// <ul class="rotaion_list"><li><a
		// href="http://see.xidian.edu.cn/html/news/7923.html"><img
		// src="/uploads/image/20160109/20160109125651_61102.jpg"
		// alt="我院刘宏伟教授团队科研成果获2015年度国家技术发明二等奖"></a></li><li><a
		// href="http://see.xidian.edu.cn/html/news/7909.html"><img
		// src="/uploads/image/20151231/20151231105648_11790.jpg"
		// alt="我校海外名师新加坡南洋理工大学Lin Weisi教授应邀来我校进行学术交流与访问"></a></li><li><a
		// href="http://see.xidian.edu.cn/html/news/7905.html"><img
		// src="/uploads/image/20151230/20151230152544_36663.jpg"
		// alt="日本东北大学陈强教授应邀到电子工程学院作报告"></a></li><li><a
		// href="http://see.xidian.edu.cn/html/news/7904.html"><img
		// src="/uploads/image/20151229/20151229204329_75030.jpg"
		// alt="2015省电赛暨陕西教师电赛颁奖我院获最高奖“TI”杯"></a></li><li><a
		// href="http://see.xidian.edu.cn/html/news/7881.html"><img
		// src="/uploads/image/20151221/20151221151031_36136.jpg"
		// alt="电院学生在全国电赛总结暨颁奖大会上被授予6项全国奖"></a></li><li><a
		// href="http://see.xidian.edu.cn/html/news/7878.html"><img
		// src="/uploads/image/20151218/20151218172220_84102.jpg"
		// alt="电院大二学生边畅获外研社杯全国英语演讲大赛总决赛二等奖"></a></li><li><a
		// href="http://see.xidian.edu.cn/html/news/7870.html"><img
		// src="/uploads/image/20151217/20151217102314_33106.jpg"
		// alt="【电院校友】专访78级校友中科院院士包为民"></a></li></ul>

		// Element contentEle = articleEle.getElementById("article_content");
		// // 处理相对路径 url，不和上面的 image url 冲突
		// Elements hrefs = contentEle.getElementsByTag("a");
		// for (int i = 0; i < hrefs.size(); i++) {
		// String origin = hrefs.get(i).attr("href");
		// System.out.println("origin: " + origin.length());
		// String newUrl = UrlTool.dealAttachmentUrl(id, origin);
		// System.out.println("newUrl: " + origin);
	}

}
