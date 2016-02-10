package com.chenxb.biz;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.chenxb.util.ColumnType;
import com.chenxb.util.UrlTool;

public class ColumnBiz {

	private static Pattern regexCountPage = Pattern.compile("\\d+/(\\d+)");

	/**
	 * 爬取本科教学、研究生、就业招聘等栏目
	 * 
	 * @param type
	 *            栏目
	 * @param currentPage
	 *            当前页码
	 * @return 返回新闻的id数组
	 * @throws IOException
	 */
	public static int[] parseColumn(int type, int currentPage) throws IOException {

		String columnUrl = UrlTool.generateUrl(type, currentPage);
		Document doc = Jsoup.connect(columnUrl).timeout(10000).get();
		Elements eles = doc.getElementById("list_area").getElementsByTag("a");
		int[] articleIds = new int[eles.size()];
		for (int i = 0; i < eles.size(); i++) {
			String url = eles.get(i).attr("href");
			articleIds[i] = Integer.parseInt(url.replaceAll("\\D+", ""));
		}
		return articleIds;
	}

	/**
	 * 根据栏目类型获取 本栏目共有几页
	 * 
	 * @param type
	 *            栏目类型
	 * @return 总页数
	 * @throws CommonException
	 * @throws IOException
	 */
	public static int getTotalPage(int type) throws IOException {
		// 最新消息栏目特殊，只有1页，没有下一页
		if (type == ColumnType.LATEST)
			return 1;
		String columnUrl = UrlTool.generateUrl(type, 1);

		// String htmlStr = HttpTool.doGet(columnUrl);

		Document doc = Jsoup.connect(columnUrl).timeout(10000).get();
		// 正则匹配 1262 条记录 1/26 页
		Element page = doc.getElementById("div_page");

		Matcher matcher = regexCountPage.matcher(page.text());
		if (matcher.find()) {
			return Integer.parseInt(matcher.group(1));
		} else {
			// 根据经验值，一个栏目至少有5页
			return 5;
		}
	}
	
	/**
	 * 获取某页码的新闻个数
	 * @param type
	 * @param indexPage 从第1页开始
	 * @return
	 * @throws IOException
	 */
	public static int countArticles(int type,int indexPage) throws IOException {
		return parseColumn(type, indexPage).length;
	}

}