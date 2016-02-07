package com.chenxb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import com.chenxb.model.ArticleItem;
import com.chenxb.util.MysqlTool;

/**
 * 插入新闻纪录到 mysql
 * 从 mysql 获取某条新闻
 * @author tomchen
 *
 */
public class ArticleDao {
	private Connection connection;

	public ArticleDao() throws Exception {
		connection = new MysqlTool().getConnection();
	}

	/**
	 * 根据 id 从数据库中获取新闻
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public ArticleItem getArticleById(int id) throws SQLException {
		// the mysql select statement
		String query = "select * from latest where id = ?";

		// create the mysql preparedstatement
		PreparedStatement preparedStmt = connection.prepareStatement(query);
		preparedStmt.setInt(1, id);

		ResultSet rs = preparedStmt.executeQuery();
		while (rs.next()) {
			String[] imageUrls = rs.getString(2).replace("[", "").replace("]", "").split(", ");
			String title = rs.getString(3);
			String date = rs.getDate(4).toString();
			int readTimes = rs.getInt(5);
			String source = rs.getString(6);
			String body = rs.getString(7);
			ArticleItem article = new ArticleItem(id, imageUrls, title, date, readTimes, source, body);
			return article;
		}
		return null;
	}

	/**
	 * 将记录插入到数据库中
	 * @param tableNmae 数据库名称，从 TableName 类中选取
	 * @param article
	 * @return
	 * @throws SQLException
	 */
	public int insertArticle(String tableName, ArticleItem article) throws SQLException {
		// the mysql insert statement
		String query = " insert into " + tableName + " (id, image_urls, title, publish_date, read_times,source,body)"
				+ " values (?, ?, ?, ?, ?,?,?)";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = connection.prepareStatement(query);
		preparedStmt.setInt(1, article.getId());
		preparedStmt.setString(2, Arrays.toString(article.getImageUrls()));
		preparedStmt.setString(3, article.getTitle());
		preparedStmt.setDate(4, Date.valueOf(article.getPublishDate()));
		preparedStmt.setInt(5, article.getReadTimes());
		preparedStmt.setString(6, article.getSource());
		preparedStmt.setString(7, article.getBody());
		return preparedStmt.executeUpdate();
	}

}
