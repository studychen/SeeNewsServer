package com.chenxb.biz;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
	public List<String> parseImages() throws IOException {
		Document doc = Jsoup.connect(Constant.SEE_URL).timeout(10000).get();
		Element ele = doc.getElementsByClass("rotaion_list").get(0);

		System.out.println(ele);
		return null;
	}

}
