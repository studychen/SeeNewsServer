package com.chenxb.test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.chenxb.dao.ArticleDao;
import com.chenxb.dao.SummaryDao;
import com.hankcs.hanlp.HanLP;

/**
 * 中文分词
 * 提取文章摘要
 * @author tomchen
 *
 */
public class TestHanlp {
	public static void main(String[] args) throws SQLException, Exception {

		for (int i = 7948; i >= 7896; i--) {
			System.out.println(new SummaryDao().updateSummary(0, i));
		}

	}

	public static String get(int id) throws SQLException, Exception {
		String document = new ArticleDao().getArticleByTypeId(0, id).getBody();
		Document doc = Jsoup.parse(document);
		List<String> sentenceList = HanLP.extractSummary(doc.text(), 2);

		if (!sentenceList.isEmpty()) {
			String summary = sentenceList.toString();
			String temp = summary.substring(1, summary.length() - 1);
			temp = temp.replaceAll("&" + "nbsp;", "");
			// unicode 空格是160
			temp = temp.replaceAll(String.valueOf((char) 160), "");
			System.out.println("util===" + temp.trim());
			// 将多个空格替换为1个空格
			return temp.trim().replaceAll("\\s+", " ") + "。";
		} else {
			return "";
		}
	}

}
