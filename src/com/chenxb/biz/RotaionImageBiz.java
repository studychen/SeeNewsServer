package com.chenxb.biz;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.chenxb.model.SimpleArticleItem;
import com.chenxb.util.Constant;

/**
 * 首页轮播图片
 * @author tomchen
 *
 */
public class RotaionImageBiz {

	/**
	 * 爬取主页的轮播图片
	 * @throws IOException 
	 * 
	*/
	public List<SimpleArticleItem>parseImages() throws IOException {
		Document doc = Jsoup.connect(Constant.SEE_URL).timeout(10000).get();
		Elements eles = doc.getElementById("list_area").getElementsByTag("a");
		int[] articleIds = new int[eles.size()];
		for (int i = 0; i < eles.size(); i++) {
			String url = eles.get(i).attr("href");
			articleIds[i] = Integer.parseInt(url.replaceAll("\\D+", ""));
		}
		return null;
	}

	
}

