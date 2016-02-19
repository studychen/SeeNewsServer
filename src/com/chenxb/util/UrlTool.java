package com.chenxb.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class UrlTool {
	// LATEST,//最新消息
	// NOTIFIC, //校园通知
	// BACHELOR, //本科教学 学士
	// MASTER, //研究生 硕士
	// RESEARCH, //科研
	// ACADEMIC //学术交流

	private static final String LATEST_URL = "http://see.xidian.edu.cn/index.php/index/more";
	// 格式为http://see.xidian.edu.cn/html/category/5/2.html
	private static final String NOTIFIC_URL = "http://see.xidian.edu.cn/html/category/";

	public static final Pattern VALID_EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	/**
	 * 
	 * @param type
	 * @param currentPage
	 *            不是无限大，有一定范围
	 * @return
	 */
	public static String generateUrl(int type, int currentPage) {
		currentPage = currentPage > 0 ? currentPage : 1;
		switch (type) {
		case ColumnType.LATEST:
			return LATEST_URL;
		case ColumnType.NOTIFIC:
		case ColumnType.BACHELOR:
		case ColumnType.MASTER:
		case ColumnType.ACADEMIC:
		case ColumnType.JOB:
			return NOTIFIC_URL + type + "/" + currentPage + ".html";
		default:
			return LATEST_URL;
		}
	}

	/**
	 * 这儿的tag <a> 不和 <image> 冲突
	 * 
	 * 处理文章 body 里的 url
	 * /uploads/file/20150706/20150706094631_73253.doc
	 * 
	 * 相对路径全部转化为绝对路径
	 * @param originTrim
	 * @return
	 */
	public static String dealAttachmentUrl(int currentPage, String origin) {
		// 去掉首尾的空格
		String originTrim = origin.trim();
		// 附件不一定都是在 uploads 文件夹下面
		// 也有可能外链到其他网站的图片/uploads/image/20141120/20141120**.jpg
		// /news/Upload/2006051811250740787.xls
		if (StringUtils.startsWithAny(originTrim, "/uploads", "/news/Upload","/news/Images")) {
			// 相对路径，比如
			return Constant.SEE_URL + originTrim;
		} else if (StringUtils.startsWithAny(originTrim, Constant.HTTP_PREFIX, Constant.HTTPS_PREFIX,
				Constant.FTP_PREFIX, Constant.JS_PREFIX)) {
			// http https ftp 开头
			// 先后顺序，先判断是不是 http 开头，再判断是不是 www 开头
			if (Constant.DEBUG) {
				System.out.println("in dealAttachmentUrl 全路径");
			}
			return originTrim;
		} else if (originTrim.length() == 0 || originTrim.equals("")) {
			// 无效<a>标签，获得的href为""
			return originTrim;
		} else if (originTrim.startsWith(Constant.MAILTO_PREFIX)) {
			return originTrim;
		} else if (VALID_EMAIL_REGEX.matcher(originTrim).find()) {
			// 只是邮件名，加上 mailto
			// 注意前后顺序
			return Constant.MAILTO_PREFIX + originTrim;
		} else if (originTrim.startsWith(Constant.WWW_PREFIX)) {
			// www开头的，加上 http://
			return Constant.HTTP_PREFIX + originTrim;
		} else if (originTrim.equals(Constant.WEBSITE_NAME)) {
			return Constant.SEE_URL;
		} else {
			// 链接未在考虑范围内，发邮件通知
			StringBuilder builder = new StringBuilder();
			builder.append("<p>UrlTool.dealAttachmentUrl() 无法解析url</p>");
			builder.append("<p>异常 href = " + originTrim + "</p>");
			MailTool.sendException(builder.toString(), currentPage, MailTool.HREF_UNUSUAL);
			return originTrim;
		}
	}

}
