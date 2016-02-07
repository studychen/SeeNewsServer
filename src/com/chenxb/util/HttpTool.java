package com.chenxb.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpTool {
	/**
	 * 
	 * @param urlStr
	 *            网页链接
	 * @return 网页的 html 源码
	 * @throws Exception
	 * @throws CommonException
	 * @throws IOException
	 */
	public static String doGet(String urlStr) throws Exception {
		URL url;
		String html = "";
		url = new URL(urlStr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(5000);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		if (connection.getResponseCode() == 200) {
			InputStream in = connection.getInputStream();
			html = StreamTool.inToStringByByte(in);
			in.close();
		} else {
			throw new Exception("新闻服务器返回值不为200");
		}
		return html;
	}

}
