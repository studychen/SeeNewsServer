package com.chenxb.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamTool {

	/**
	 * 利用ByteArrayOutputStream将流转化为字符串
	 * 
	 * @param in
	 *            需要读取的InputStream
	 * @return 读取的字符串
	 * @throws Exception
	 */
	public static String inToStringByByte(InputStream in) throws Exception {
		ByteArrayOutputStream outStr = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		// 这部分有问题，一个中文3个byte，如何确定1024最末尾的正好是一个中文
		int len = 0;
		StringBuilder content = new StringBuilder();
		while ((len = in.read(buffer)) != -1) {
			content.append(new String(buffer, 0, len, "UTF-8"));
		}
		outStr.close();
		return content.toString();
	}

	/**
	 * 利用BufferedReader将流转化为字符串
	 * 
	 * @param in
	 *            需要读取的InputStream
	 * @return 读取的字符串
	 * @throws Exception
	 */
	public static String inToStringByReader(InputStream in) throws Exception {
		BufferedReader reader = null;
		StringBuilder content = new StringBuilder();
		reader = new BufferedReader(new InputStreamReader(in));
		String line = "";
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			content.append(line);
		}
		return content.toString();
	}

}
