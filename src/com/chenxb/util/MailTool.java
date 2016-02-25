package com.chenxb.util;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

public class MailTool {
	/**
	 * 将爬虫抛出异常的url、堆栈信息发送邮件
	 * 
	 * @param content
	 *            邮件类型
	 * @param type
	 *            爬虫错误代码
	 * 
	 * @return 邮件是否发送成功
	 */

	// 图片异常，不是以 /uploads 开头
	public static final int IMAGE_UNUSUAL = 0;
	public static final int ARTICLE_ITEM_BIZ = 1;
	public static final int HREF_UNUSUAL = 2;

	public static boolean sendException(String content, int currentPage, int type) {
		// 配置信息支持从文件读取 props.load(InputStream inStream);
		Properties props = new Properties();

		// 调试的时候需开启debug调试
		props.setProperty("mail.debug", "false");
		// 发送服务器需要身份验证
		props.setProperty("mail.smtp.auth", "true");
		// 设置邮件服务器主机名
		props.setProperty("mail.host", "smtp.qq.com");
		// 发送邮件协议名称
		props.setProperty("mail.transport.protocol", "smtp");

		MailSSLSocketFactory sf;
		try {
			sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.ssl.socketFactory", sf);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			return false;
		}

		// 根据配置文件生成一个 session 对象
		Session session = Session.getInstance(props);

		// 发件人邮箱用户名、密码，连接到邮件服务器，
		Transport transport;
		try {
			transport = session.getTransport();
			transport.connect("smtp.qq.com", "905073281@qq.com", "*********");

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		// 创建邮件
		Message msg = new MimeMessage(session);
		// 邮件主题，也就是标题
		try {
			msg.setSubject("seenews 错误 type " + type);
			// 邮件内容，支持 html 格式
			StringBuilder builder = new StringBuilder(content);
			builder.append("<p>新闻页面 url = " + Constant.ARTICLE_BASE_URL + currentPage + ".html" + "</p>");
			builder.append("<p>异常发生时间 " + TimeTool.getCurrentTime() + "</p>");
			// /* 设置Content 浏览器解析编码和格式等 */
			msg.setContent(builder.toString(), "text/html;charset=utf-8");
			// 设置发件人的邮箱
			msg.setFrom(new InternetAddress("905073281@qq.com"));

			// 给收件人的地址发送上面的 Message
			transport.sendMessage(msg, new Address[] { new InternetAddress("studychen@foxmail.com") });
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}

		// 无异常抛出，表示发送成功
		return true;
	}
}
