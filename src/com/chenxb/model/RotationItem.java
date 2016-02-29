package com.chenxb.model;

/**
 * 首页轮播图片 javabean
 * @author tomchen
 *
 */
public class RotationItem {

	int id;
	// 首页只有一张图片
	String[] imageUrls;
	String title;
	// type 是数字 1表示新闻通知 2本科教学 见 ColumnType
	int type;

	public RotationItem(int id, String[] imageUrls, String title, int type) {
		this.id = id;
		this.imageUrls = imageUrls;
		this.title = title;
		this.type = type;
	}

	@Override
	public String toString() {
		return "RotationItem [id=" + id + ", imageUrls=" + imageUrls + ", title=" + title + ", type=" + type + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String[] getImageUrl() {
		return imageUrls;
	}

	public void setImageUrl(String[] imageUrls) {
		this.imageUrls = imageUrls;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
