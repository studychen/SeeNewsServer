package com.chenxb.biz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.chenxb.model.RotationItem;
import com.chenxb.util.Constant;
import com.chenxb.util.ImageTool;

/**
 * 首页轮播图片
 * @author tomchen
 *
 */
public class RotationImageBiz {

	/**
	 * 爬取主页的轮播图片
	 * @throws Exception 
	 * 
	*/
	public static List<RotationItem> parseHomeRotaions() throws Exception {
		Document doc = Jsoup.connect(Constant.SEE_URL).timeout(10000).get();
		Elements eles = doc.getElementsByClass("rotaion_list").get(0).getElementsByTag("a");
		List<RotationItem> rotaions = new ArrayList<RotationItem>(eles.size());

		for (Element e : eles) {

			String articleUrl = e.attr("href");

			int id = Integer.parseInt(articleUrl.replaceAll("\\D+", ""));

			Element imgEle = e.getElementsByTag("img").get(0);

			String imageUrl = imgEle.attr("src");

			String[] key = { ImageTool.convertUrl(id, imageUrl) };

			String title = imgEle.attr("alt");

			// 该 id 的新闻属于哪个栏目
			int type = ArticleBiz.getType(id);

			rotaions.add(new RotationItem(id, key, title, type));
		}
		return rotaions;
	}

}
