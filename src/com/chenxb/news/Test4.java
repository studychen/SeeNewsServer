package com.chenxb.news;

import java.sql.Time;

import com.chenxb.biz.ArticleBiz;
import com.chenxb.biz.ColumnBiz;
import com.chenxb.dao.ColumnDao;
import com.chenxb.model.ArticleItem;
import com.chenxb.util.ColumnType;
import com.chenxb.util.TableName;
import com.chenxb.util.TimeTool;

public class Test4 {

	public static void main(String[] args) throws Exception {
		System.out.println(TimeTool.getCurrentTime());

		// String x = "http://jwc.xidian.edu.cn/images/ico/doc.jpg";
		// System.out.println(StringUtils.endsWithAny(x,
		// Constant.DOC_JPG_SUFFIX, Constant.XLS_JPG_SUFFIX,
		// Constant.RAR_JPG_SUFFIX, Constant.ZIP_JPG_SUFFIX));
		// String origin = "bhhan@mail.xdian.edu.cn";
		// System.out.println(VALID_EMAIL_REGEX.matcher(origin).find());
		//
		// String x
		// ="file://C:/Users/ADMINI~1/AppData/Local/Temp/msohtmlclip1/01/clip_image001.gif";
		// System.out.println(x.startsWith("file://C"));
		// int id = 7465;
		// // 根据后缀的数字，拼接新闻 url
		// String urlStr = Constant.ARTICLE_BASE_URL + id + ".html";
		// Document doc = Jsoup.connect(urlStr).timeout(10000).get();
		// // 去掉jsoup对html字符串加的"\n"，方便json字符串返回
		// doc.outputSettings().prettyPrint(false);
		//
		// Element articleEle = doc.getElementById("article");
		//
		// Element contentEle = articleEle.getElementById("article_content");
		// // 处理相对路径 url，不和上面的 image url 冲突
		// Elements hrefs = contentEle.getElementsByTag("a");
		// for (int i = 0; i < hrefs.size(); i++) {
		// String origin = hrefs.get(i).attr("href");
		// System.out.println("origin: " + origin.length());
		// String newUrl = UrlTool.dealAttachmentUrl(id, origin);
		// System.out.println("newUrl: " + origin);
		// }
	}
	// originhttp://www.ciscn.cn
	// newUrlhttp://www.ciscn.cn
	// originwww.ciscn.cn
	// newUrlwww.ciscn.cn
	// originhttp://www.ciscn.cn
	// newUrlhttp://www.ciscn.cn
	// originhttp://www.ciscn.cn/newstextInfo.action?newid=1231
	// newUrlhttp://www.ciscn.cn/newstextInfo.action?newid=1231

}
