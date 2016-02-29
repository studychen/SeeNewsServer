package com.chenxb.jpush;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chenxb.dao.ArticleDao;
import com.chenxb.model.ArticleItem;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.ClientConfig;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.SMS;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class TestJpush {

	private static final String appKey = "8c4911096188db2e7f2b370c";
	private static final String masterSecret = "1cd48b15285f5c6f100f46d4";
	public static final String ALERT = "救助郭燕-电院2000级校友，参与互联网众筹，通过网络传递爱心！";

	public static final String TITLE = "电院最新资讯";

	protected static final Logger LOG = LoggerFactory.getLogger(TestJpush.class);

	public static void main(String[] args) {

		JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
		// For push, all you need do is to build PushPayload object.
		PushPayload payload = buildPushObject_android_tag_alertWithTitle();

		System.out.println("PushPayload 信息" + payload.toString());

		try {
			PushResult result = jpushClient.sendPush(payload);
			LOG.info("Got result - " + result);

		} catch (APIConnectionException e) {
			// Connection error, should retry later
			LOG.error("Connection error, should retry later", e);

		} catch (APIRequestException e) {
			// Should review the error, and fix the request
			LOG.error("Should review the error, and fix the request", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
		}

	}

	public static PushPayload buildPushObject_android_tag_alertWithTitle() {
		try {
			return PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.all())
					.setNotification(Notification.android(ALERT, TITLE, getArticleExtraInfo())).build();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, String> getArticleExtraInfo() throws SQLException, Exception {
		ArticleItem article = new ArticleDao().getArticleByTypeId(0, 7948);
		Map<String, String> extras = new HashMap<String, String>();

		extras.put("type", "0");
		extras.put("id", article.getId() + "");
		extras.put("publishDate", article.getPublishDate());
		extras.put("readTimes", article.getReadTimes() + "");
		
		return extras;
	}

}
