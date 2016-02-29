package com.chenxb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.chenxb.model.ArticleItem;
import com.chenxb.util.MysqlTool;
import com.chenxb.util.TableName;
import com.hankcs.hanlp.HanLP;

/**
 * 增加文章的摘要
 * 可以先把数据存入 mysql，再读取mysql 的数据增加摘要
 * 也可以爬虫的时候就把摘要存入 mysql
 * @author tomchen
 *
 */
public class SummaryDao {
	private Connection connection;

	public SummaryDao() throws Exception {
		connection = new MysqlTool().getConnection();
	}

	/**
	 * 根据 type 找到数据库表名称
	 * 再从该表里找出 id 对应的新闻
	 * @param type 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public int updateSummary(int type, int id) throws SQLException {
		// 根据 type 找出对应的 table 名称
		String tableName = TableName.getTableByType(type);

		String body = getArticleBody(type, id);
		// body是 html 表示的
		Document doc = Jsoup.parse(body);
		List<String> sentenceList = HanLP.extractSummary(doc.text(), 3);

		// 如果摘要是空，不采取任何操作
		if (sentenceList.isEmpty()) {

			String update = "update " + tableName + " set summary = title WHERE id= ?";

			// create the mysql preparedstatement
			PreparedStatement preparedStmt = connection.prepareStatement(update);
			preparedStmt.setInt(1, id);

			return preparedStmt.executeUpdate();
		} else {
			String summary = sentenceList.toString();
			// 去掉 list 首尾的[ 和 ]
			summary = summary.substring(1, summary.length() - 1);
			summary = summary.replaceAll("&" + "nbsp;", "");
			// unicode 空格是160
			summary = summary.replaceAll(String.valueOf((char) 160), "");
			// 将多个空格替换为1个空格
			summary = summary.trim().replaceAll("\\s+", " ") + "。";

			String update = "update " + tableName + " set summary = ? WHERE id= ?";

			// create the mysql preparedstatement
			PreparedStatement preparedStmt = connection.prepareStatement(update);
			preparedStmt.setString(1, summary);
			preparedStmt.setInt(2, id);
			return preparedStmt.executeUpdate();
		}

	}

	/**
	 * 找出新闻的主体内容
	 * @param type 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public String getArticleBody(int type, int id) throws SQLException {
		// 根据 type 找出对应的 table 名称
		String tableName = TableName.getTableByType(type);

		// the mysql select statement
		String query = "select body from " + tableName + " where id = ?";

		// create the mysql preparedstatement
		PreparedStatement preparedStmt = connection.prepareStatement(query);
		preparedStmt.setInt(1, id);

		ResultSet rs = preparedStmt.executeQuery();
		if (rs.next()) {
			return rs.getString(1);
		}
		return "";
	}

}
