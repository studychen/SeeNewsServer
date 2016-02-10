package com.chenxb.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.StringUtils;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.util.Auth;

public class ImageTool {

	// 附件下载的图标，忽略
	private static final String IMAGE_BASE = "/uploads/image";
	private static final String IMAGE_OLD__BASE = "/uploads/old";

	/**
	 * 图片上传到七牛，和原来的 imageUrl 不相等 
	 * 否则还是返回原来的 url
	 * @param currentPage
	 * @param origin
	 * @return
	 */
	public static String convertUrl(int currentPage, String origin) {
		// 图片资源不一定都是在 uploads 文件夹下面
		// 也有可能外链到其他网站的图片
		if (origin.startsWith(Constant.HTTP_PREFIX)) {
			// 以绝对路径开头，最前面是网站域名
			// 比如 http://see.xidian.edu.cn/uploads/image/20141120/201411**.png
			// http://imgtec.eetrend.com/sites/***
			String imageKey = StringTool.createMD5(origin);
			uploadByUrl(currentPage, origin, imageKey);
			return imageKey;
		} else if (origin.contains(IMAGE_BASE)) {
			// 相对路径，比如/uploads/image/20141120/20141120**.jpg
			// /Public/kindeditor/php/../../../uploads/image/20151116/20151116114927_39484.jpg
			// 把图片上传给七牛
			// if 的先后顺序，先判断是否是全路径，再判断是不是相对路径
			String wholeURl = Constant.SEE_URL + origin;

			String imageKey = StringTool.createMD5(origin);

			uploadByUrl(currentPage, wholeURl, imageKey);
			return imageKey;
		} else if (origin.startsWith(IMAGE_OLD__BASE)) {
			// 老图片路径 /uploads/old
			String wholeURl = Constant.SEE_URL + origin;

			String imageKey = StringTool.createMD5(origin);

			uploadByUrl(currentPage, wholeURl, imageKey);
			return imageKey;

		} else {
			// 这部分 todo，识别其他格式的图片
			// 或者试图访问这个图片，但失败了，则不是完整的 url
			StringBuilder builder = new StringBuilder();
			builder.append("<p>ImageTool.convertUrl() 无法解析图片</p>");
			builder.append("<p>图片 url = " + origin + "</p>");
			MailTool.sendException(builder.toString(), currentPage, MailTool.IMAGE_UNUSUAL);
			return origin;
		}

	}

	/** imageKey 为输入参数
	 * 
	 * @param currentPage
	 * @param origin
	 * @param imageKey
	 * @return
	 */
	public static String convertUrl(int currentPage, String origin, String imageKey) {
		// 图片资源不一定都是在 uploads 文件夹下面
		// 也有可能外链到其他网站的图片
		if (origin.startsWith(Constant.HTTP_PREFIX)) {
			// 以绝对路径开头，最前面是网站域名
			// 比如 http://see.xidian.edu.cn/uploads/image/20141120/201411**.png
			// http://imgtec.eetrend.com/sites/***
			uploadByUrl(currentPage, origin, imageKey);
			return imageKey;
		} else if (origin.startsWith(IMAGE_BASE)) {
			// 相对路径，比如/uploads/image/20141120/20141120**.jpg
			// /Public/kindeditor/php/../../../uploads/image/20151116/20151116114927_39484.jpg
			// 把图片上传给七牛
			// if 的先后顺序，先判断是否是全路径，再判断是不是相对路径
			String wholeURl = Constant.SEE_URL + origin;

			uploadByUrl(currentPage, wholeURl, imageKey);
			return imageKey;
		} else if (origin.startsWith(IMAGE_OLD__BASE)) {
			// 老图片路径 /uploads/old
			String wholeURl = Constant.SEE_URL + origin;

			uploadByUrl(currentPage, wholeURl, imageKey);
			return imageKey;

		} else {
			// 这部分 todo，识别其他格式的图片
			// 或者试图访问这个图片，但失败了，则不是完整的 url
			StringBuilder builder = new StringBuilder();
			builder.append("<p>ImageTool.convertUrl() 无法解析图片</p>");
			builder.append("<p>图片 url = " + origin + "</p>");
			MailTool.sendException(builder.toString(), currentPage, MailTool.IMAGE_UNUSUAL);
			return origin;
		}

	}

	/**
	 * 
	 * @param url
	 *            给定图片的 url
	 * @return 将图片上传至七牛，返回七牛上图片的 url
	 * @throws QiniuException
	 */
	private static void uploadByUrl(int currentPage, String originalUrl, String key) {
		FetchRunnable f = new FetchRunnable(currentPage, originalUrl, key);
		new Thread(f).start();
	}

}

/**
 * 图片上传使用多线程
 * 有问题？上传失败如何回滚？
 * 失败的概率很小，暂时不考虑
 * 或者失败了发邮件通知
 * @author tomchen
 *
 */
class FetchRunnable implements Runnable {
	private static final String ACCESS_KEY = "0JdTNdUAyxP1i4GuAPzb2lF-HpELjf12ZvcQ7f0a"; // 你的access_key
	private static final String SECRET_KEY = "rsorpEMhIC3rYxud9StCH15-jbAOkyPAD51zx_eG"; // 你的secret_key
	private static final String BUCKET_NAME = "seenews"; // 你的secret_key

	private int currentPage;
	private String url;
	private String key;

	public FetchRunnable(int currentPage, String url, String key) {
		this.currentPage = currentPage;
		this.url = url;
		this.key = key;
	}

	@Override
	public void run() {
		// 获取到 Access Key 和 Secret Key 之后，您可以按照如下方式进行密钥配置
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		// 获取空间管理器
		BucketManager bucketManager = new BucketManager(auth);
		try {
			// 要求url可公网正常访问BucketManager.fetch(url, bucketName, key);
			// @param url 网络上一个资源文件的URL
			// @param bucketName 空间名称
			// @param key 空间内文件的key[唯一的]
			bucketManager.fetch(url, BUCKET_NAME, key);
		} catch (QiniuException e) {
			// 处理已知的部分资源不存在
			if (StringUtils.endsWithAny(url, Constant.DOC_JPG_SUFFIX, Constant.XLS_JPG_SUFFIX, Constant.RAR_JPG_SUFFIX,
					Constant.ZIP_JPG_SUFFIX)) {
				// 已经手工上传了这几种图标
				return;
			}

			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));

			StringBuilder builder = new StringBuilder(errors.toString());
			builder.append("<p> ImageTool.uploadByUrl(url, key)发生异常！</p>");
			builder.append("<p> url = " + url + "</p>");
			builder.append("<p> key = " + key + "</p>");
			MailTool.sendException(builder.toString(), currentPage, MailTool.ARTICLE_ITEM_BIZ);

			// to do 失败了邮件通知
		}
	}
}
