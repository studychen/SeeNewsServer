package com.chenxb.util;

import java.util.regex.Pattern;

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
	 * 主要是处理文件下载的相对路径和绝对路径
	 * @param origin
	 * @return
	 */
	public static String dealAttachmentUrl(int currentPage, String origin) {
		// 附件不一定都是在 uploads 文件夹下面
		// 也有可能外链到其他网站的图片/uploads/image/20141120/20141120**.jpg
		if (origin.startsWith("/uploads")) {
			// 相对路径，比如
			return Constant.SEE_URL + origin;
		} else if (origin.length() == 0 || origin.equals("")) {
			// 无效<a>标签，获得的href为""
			return origin;
		} else if (origin.startsWith(Constant.MAILTO_PREFIX)) {
			return origin;
		} else if (VALID_EMAIL_REGEX.matcher(origin).find()) {
			// 只是邮件名，加上 mailto
			// 注意前后顺序
			return Constant.MAILTO_PREFIX + origin;
		} else if (origin.startsWith(Constant.HTTP_PREFIX) || origin.startsWith(Constant.WWW_PREFIX)
				|| origin.contains(Constant.EDU_PREFIX)) {
			return origin;
		} else if (origin.equals(Constant.WEBSITE_NAME)){
			return Constant.SEE_URL;
		} else{
			// 链接未在考虑范围内，发邮件通知
			StringBuilder builder = new StringBuilder();
			builder.append("<p>UrlTool.dealAttachmentUrl() 无法解析url</p>");
			builder.append("<p>图片 url = " + origin + "</p>");
			MailTool.sendException(builder.toString(), currentPage, MailTool.HREF_UNUSUAL);
			return origin;
		}
	}

}
