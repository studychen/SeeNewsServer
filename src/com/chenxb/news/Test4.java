package com.chenxb.news;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.chenxb.biz.ArticleBiz;
import com.chenxb.biz.ColumnBiz;
import com.chenxb.dao.ColumnDao;
import com.chenxb.model.ArticleItem;
import com.chenxb.util.ColumnType;
import com.chenxb.util.Constant;
import com.chenxb.util.ImageTool;
import com.chenxb.util.TableName;
import com.chenxb.util.TimeTool;
import com.chenxb.model.RotaionItem;

public class Test4 {

	public static void main(String[] args) throws Exception {
		Document doc = Jsoup.connect(Constant.SEE_URL).timeout(10000).get();
		Elements eles = doc.getElementsByClass("rotaion_list").get(0).getElementsByTag("a");
		List<RotaionItem> rotaions = new ArrayList<RotaionItem>(eles.size());

		for (Element e : eles) {

			String articleUrl = e.attr("href");

			int id = Integer.parseInt(articleUrl.replaceAll("\\D+", ""));

			String imageUrl = e.getElementsByTag("img").get(0).attr("alt");
			String key = ImageTool.convertUrl(id, imageUrl);

			String title = e.getElementsByTag("img").get(0).attr("alt");
		
			rotaions.add(new RotaionItem(id, key, title));
		}

		System.out.println(rotaions);
	}

}
