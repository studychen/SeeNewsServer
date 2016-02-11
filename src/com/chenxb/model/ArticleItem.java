package com.chenxb.model;

import java.util.Arrays;

/**
 * model/ItemNews.java 新闻详情页面用到的完整实体类 新闻实体类 包括标题，发布日期，阅读次数，新闻主体内容等
 * 
 * @author tomchen
 *
 */

public class ArticleItem {

	private int id;
	// 图片资源不是必须的
	private String[] imageUrls;
	private String title;
	private String publishDate;
	private int readTimes;
	private String source;
	private String body;

	public ArticleItem(int id, String[] imageUrls, String title, String publishDate, int readTimes, String source,
			String body) {
		this.id = id;
		this.imageUrls = imageUrls;
		this.title = title;
		this.publishDate = publishDate;
		this.readTimes = readTimes;
		this.source = source;
		this.body = body;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String[] getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(String[] imageUrls) {
		this.imageUrls = imageUrls;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public int getReadTimes() {
		return readTimes;
	}

	public void setReadTimes(int readTimes) {
		this.readTimes = readTimes;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "ArticleItem [id=" + getId() + ",\n imageUrls=" + Arrays.toString(getImageUrls()) + ",\n title="
				+ getTitle() + ",\n publishDate=" + getPublishDate() + ",\n source=" + source + ",\n readTimes="
				+ getReadTimes() + ",\n body=" + body + "]";
	}

}