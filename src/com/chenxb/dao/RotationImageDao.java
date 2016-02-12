package com.chenxb.dao;

import static com.chenxb.util.Constant.DEBUG;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.chenxb.biz.ArticleBiz;
import com.chenxb.biz.ColumnBiz;
import com.chenxb.biz.RotationImageBiz;
import com.chenxb.model.ArticleItem;
import com.chenxb.model.RotationItem;
import com.chenxb.util.Constant;
import com.chenxb.util.MysqlTool;
import com.chenxb.util.TableName;
import com.chenxb.util.TimeTool;

/**
 * 将首页轮播图片新闻，插入到 Mysql 中
 * @author tomchen
 *
 */
public class RotationImageDao {
	private Connection connection;
	private static final String TABLE_RATATION = "rotation";

	public RotationImageDao() throws Exception {
		connection = new MysqlTool().getConnection();
	}

	/**
	 * @throws Exception 
	 * 
	 */
	public void initRotations() throws Exception {

		List<RotationItem> rotations = RotationImageBiz.parseHomeRotaions();

		if (rotations == null || rotations.isEmpty())
			return;

		for (RotationItem rotation : rotations) {
			if (DEBUG) {
				System.out.println(TimeTool.getCurrentTime() + " insert " + rotation.getId() + " " + rotation.getTitle()
						+ " type " + rotation.getType() + " into " + TABLE_RATATION);
			}
			insertRotationItem(rotation);
			// 等待时间，避免对被爬取的网站负载过大
			TimeTool.sleepSomeTime();
		}
	}

	/**
	 * 从数据库中获取 多条 轮播图片记录
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	public List<RotationItem> getTopRotations() throws SQLException {

		String query = "select * from " + TABLE_RATATION + " order by id desc limit " + Constant.ROTATION_AMOUNT;

		PreparedStatement preparedStmt = connection.prepareStatement(query);

		ResultSet rs = preparedStmt.executeQuery();

		List<RotationItem> rotations = new ArrayList<RotationItem>(Constant.ROTATION_AMOUNT);

		while (rs.next()) {
			int id = rs.getInt(1);
			String imageUrl = rs.getString(2);
			String title = rs.getString(3);
			int type = rs.getInt(4);
			rotations.add(new RotationItem(id, imageUrl, title, type));
		}
		return rotations;
	}

	public int insertRotationItem(RotationItem rotation) throws SQLException {
		// the mysql insert statement
		// 根据 type 找到某条新闻属于的栏目
		String query = " insert ignore into " + TABLE_RATATION + " (id, image_url, title, type)"
				+ " values (?, ?, ?, ?)";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = connection.prepareStatement(query);
		preparedStmt.setInt(1, rotation.getId());
		preparedStmt.setString(2, rotation.getImageUrl());
		preparedStmt.setString(3, rotation.getTitle());
		preparedStmt.setInt(4, rotation.getType());
		return preparedStmt.executeUpdate();
	}

}