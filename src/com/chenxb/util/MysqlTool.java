package com.chenxb.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.sina.sae.util.SaeUserInfo;

public class MysqlTool {

	// 是否使用新浪云
	private static boolean isCloud = false;

	public MysqlTool() {
		// JDBC驱动程序
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 为了方便分析错误，将异常全部抛出到最顶层
	public Connection getConnection() throws Exception {

		// 后面unicode和utf8设置防止中文乱码
		String url = "jdbc:mysql://127.0.0.1:3306/see_news?useSSL=false&useUnicode=true&characterEncoding=utf-8";
		String name = "root";
		String password = "chenxb123";

		if (isCloud) {
			String appName = SaeUserInfo.getAppName();
			String mysqlName = "app_" + appName;
			url = "jdbc:mysql://w.rdc.sae.sina.com.cn:3307/" + mysqlName + "?autoReconnect=true";
			name = SaeUserInfo.getAccessKey();
			password = SaeUserInfo.getSecretKey();
		}
		Connection con = DriverManager.getConnection(url, name, password);

		return con;

	}

}
