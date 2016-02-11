package com.chenxb.model;

/**
 * 首页轮播图片 javabean
 * @author tomchen
 *
 */
public class RotaionItem {

	int id;
	// 首页只有一张图片
	String imageUrl;
	String title;

	public RotaionItem(int id, String imageUrl, String title) {
		this.id = id;
		this.imageUrl = imageUrl;
		this.title = title;
	}

	@Override
	public String toString() {
		return "RotaionItem [id=" + id + ", imageUrl=" + imageUrl + ", title=" + title + "]";
	}
	
	

}
