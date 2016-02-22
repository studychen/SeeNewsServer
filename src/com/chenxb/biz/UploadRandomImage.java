package com.chenxb.biz;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import com.qiniu.storage.BucketManager;
import com.qiniu.util.Auth;

public class UploadRandomImage {

	private static final String RANDOM_URL = "https://unsplash.it/640/427/?image=";
	private static final String ACCESS_KEY = "***-***"; // 你的access_key
	private static final String SECRET_KEY = "***-***"; // 你的secret_key
	private static final String BUCKET_NAME = "***-***"; // 你的空间名称

	public static void main(String[] args) throws InterruptedException, IOException {
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		// 获取空间管理
		BucketManager bucketManager = new BucketManager(auth);

		int key = 0;
		for (int i = 0; i < 1025; i++) {
			// 如果 i 对应的图片存在，上传七牛
			if (exists(i)) {
				try {
					bucketManager.fetch(RANDOM_URL + i, BUCKET_NAME, key + "");
					System.out.println("i = " + i + " to key = " + key);
					key++;
				} catch (Exception e) {
					bucketManager.fetch(RANDOM_URL + i, BUCKET_NAME, key + "");
					System.out.println("Exception i = " + i + " to key = " + key);
					// 只有上传了七牛，key 才+1，保证七牛的 key 连续
					key++;
				}
				// sleep一段时间，免得对网站负载过大
			} else {
				System.out.println(i + "不存在");
			}
		}
		System.out.println(key + "最终图片数目");

	}

	/**
	 * 判断地址对于的图片是否存在
	 * @param id
	 * @return
	 */
	public static boolean exists(int id) {
		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(RANDOM_URL + id).openConnection();
			con.setRequestMethod("HEAD");
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
