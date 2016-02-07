package com.chenxb.common;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamTool {

	public static byte[] read(InputStream inputStr) throws Exception {
		ByteArrayOutputStream outStr = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inputStr.read(buffer)) != -1) {
			outStr.write(buffer, 0, len);
		}
		inputStr.close();
		return outStr.toByteArray();
	}

}
