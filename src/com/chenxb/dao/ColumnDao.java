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
import com.chenxb.model.ArticleItem;
import com.chenxb.model.SimpleArticleItem;
import com.chenxb.util.Constant;
import com.chenxb.util.MysqlTool;
import com.chenxb.util.TableName;
import com.chenxb.util.TimeTool;

/**
 * 获取某个栏目 多页新闻记录，插入到 Mysql 中
 * 为了简便，只获取前20页新闻
 * @author tomchen
 *
 */
public class ColumnDao {
	private static final int MAX_COLUMN_NUM = 30;
	private Connection connection;

	public ColumnDao() throws Exception {
		connection = new MysqlTool().getConnection();
	}

	/**
	 * @throws Exception 
	 * 
	 */
	public void initArticles(int type) throws Exception {
		int total = ColumnBiz.getTotalPage(type);
		// 该栏目的页数若过多，只爬取20页
		if (total > MAX_COLUMN_NUM) {
			total = MAX_COLUMN_NUM;
		}

		String tableName = TableName.getTableByType(type);

		// 注意从第1页开始
		for (int i = 1; i <= total; i++) {
			int[] ids = ColumnBiz.parseColumn(type, i);
			for (int id : ids) {
				ArticleItem article = ArticleBiz.parseNewsItem(id);
				if (DEBUG) {
					System.out.println(TimeTool.getCurrentTime() + " insert " + id + " " + article.getTitle() + " into "
							+ tableName);
				}
				insertArticle(tableName, article);
				// 等待时间，避免对被爬取的网站负载过大
				TimeTool.sleepSomeTime();
			}
		}
	}

	/**
	 * @throws Exception 
	 * 只爬取新闻 为了图片上传失败 重新上传
	 */
	public static void justParseArticles(int type) throws Exception {
		int total = ColumnBiz.getTotalPage(type);
		// 该栏目的页数若过多，只爬取20页
		if (total > MAX_COLUMN_NUM) {
			total = MAX_COLUMN_NUM;
		}

		String tableName = TableName.getTableByType(type);

		// 注意从第1页开始
		for (int i = 1; i <= total; i++) {
			int[] ids = ColumnBiz.parseColumn(type, i);
			for (int id : ids) {
				ArticleItem article = ArticleBiz.parseNewsItem(id);
				if (DEBUG) {
					System.out.println(TimeTool.getCurrentTime() + " insert " + id + " " + article.getTitle() + " into "
							+ tableName);
				}
				// 等待时间，避免对被爬取的网站负载过大
				TimeTool.sleepSomeTime();
			}

		}
	}

	/**
	 * 重新抓取新闻，比较当前最小的 id
	 * 把最小的 id 之前的新闻插入 mysql
	 * @throws Exception 
	 * 
	 */
	public void reInitArticles(int type) throws Exception {
		int total = ColumnBiz.getTotalPage(type);
		// 该栏目的页数若过多，只爬取20页
		if (total > MAX_COLUMN_NUM) {
			total = MAX_COLUMN_NUM;
		}

		int minId = getMinId(type);

		if (Constant.DEBUG) {
			System.out.println(TimeTool.getCurrentTime() + " get minId " + minId);
		}

		String tableName = TableName.getTableByType(type);

		boolean find = false;

		// 注意从第1页开始
		for (int i = 1; i <= total; i++) {
			int[] ids = ColumnBiz.parseColumn(type, i);

			if (!find) {
				// 数组最后一个数，是否在 mysql
				boolean isExist = isIdExist(type, ids[ids.length - 1]);

				// 如果最后一个数在 mysql 中，该页所有记录都已爬取
				if (isExist) {
					continue;
				} else {
					// 爬取到这一页中断
					int pre = 0;
					for (; pre < ids.length; pre++) {
						// 如果记录存在，继续向下寻找
						if (isIdExist(type, ids[pre])) {
							continue;
						} else {
							if (DEBUG) {
								// pre 不存在，pre-1存在
								System.out.println(TimeTool.getCurrentTime() + " " + ids[pre] + " 第一个不存在 in 第 " + (i)
										+ " 页，第 " + (pre + 1) + " 个");
							}
							// 不存在，跳槽循环
							break;
						}
					}

					for (int re = pre; re < ids.length; re++) {
						ArticleItem article = ArticleBiz.parseNewsItem(ids[re]);
						if (DEBUG) {
							System.out.println(TimeTool.getCurrentTime() + " insert " + ids[re] + " "
									+ article.getTitle() + " into " + tableName);
						}
						insertArticle(tableName, article);
						// 等待时间，避免对被爬取的网站负载过大
						TimeTool.sleepSomeTime();
					}
				}
			} else {
				// 已经找到了上次的断点 id，把往后页面全部新闻插入 mysql
				if (find) {
					for (int id : ids) {
						ArticleItem article = ArticleBiz.parseNewsItem(id);
						insertArticle(tableName, article);
						if (DEBUG) {
							System.out.println(TimeTool.getCurrentTime() + " insert " + id + " " + article.getTitle()
									+ " into " + tableName);
						}
						// 等待时间，避免对被爬取的网站负载过大
						TimeTool.sleepSomeTime();
					}
				}
			}
		}

	}

	/**
	 * 对比网站和Mysql
	 * 新增数据到 Mysql
	 * @param type
	 * @throws Exception 
	 */
	public void addArticles(int type) throws Exception {
		String tableName = TableName.getTableByType(type);

		int topId = getMaxId(type);

		int currentPage = 1;

		while (currentPage < MAX_COLUMN_NUM) {
			int[] ids = ColumnBiz.parseColumn(type, currentPage);

			int index = ArrayUtils.indexOf(ids, topId);

			// 如果当前数据库最新记录 == 网站最新记录
			if (index == 0) {
				return;
			}

			// 网站当前页包含 mysql 里的最新id
			// 把更新的记录插入到 mysql 中
			if (index > 0) {
				for (int i = 0; i < index; i++) {
					ArticleItem article = ArticleBiz.parseNewsItem(ids[i]);
					if (DEBUG) {
						System.out.println("insert " + ids[i] + " " + article.getTitle() + " into mysql");
					}
					insertArticle(tableName, article);
					// 等待时间，避免对被爬取的网站负载过大
					TimeTool.sleepSomeTime();
				}
				return;
			}

			// 最新的 id 不在当前页里
			// 需要全部更新数据
			if (index < 0) {
				for (int id : ids) {
					ArticleItem article = ArticleBiz.parseNewsItem(id);
					if (DEBUG) {
						System.out.println("insert " + id + " " + article.getTitle() + " into mysql");
					}
					insertArticle(tableName, article);
					// 等待时间，避免对被爬取的网站负载过大
					TimeTool.sleepSomeTime();
				}
				currentPage++;
			}
		}
	}

	/**
	 * 返回某个表 最新的Constant.EACH_AMOUNT条新闻
	 * 只是 listview 展示
	 * 分页展示，需要 type 和偏移id
	 * @param type
	 * @param threshold
	 * @return
	 * @throws SQLException 
	 */
	public List<SimpleArticleItem> getTopSimpleArticles(int type, int offset) throws SQLException {
		String tableName = TableName.getTableByType(type);

		//这儿两个 sql 语句要同步修改
		String query = "select id,image_urls,title,publish_date,read_times from " + tableName
				+ " where id < ? order by id desc limit " + Constant.EACH_AMOUNT;

		PreparedStatement preparedStmt = connection.prepareStatement(query);

		preparedStmt.setInt(1, offset);

		// 如果是首页 这是很少的情况
		if (offset == -1) {
			query = "select id,image_urls,title,publish_date,read_times from " + tableName + " order by id desc limit "
					+ Constant.EACH_AMOUNT;
			preparedStmt = connection.prepareStatement(query);
		}

		ResultSet rs = preparedStmt.executeQuery();

		List<SimpleArticleItem> articles = new ArrayList<SimpleArticleItem>(Constant.EACH_AMOUNT);

		while (rs.next()) {
			int id = rs.getInt(1);
			String[] imageUrls = rs.getString(2).replace("[", "").replace("]", "").split(", ");
			String title = rs.getString(3);
			String date = rs.getDate(4).toString();
			int readTimes = rs.getInt(5);
			SimpleArticleItem article = new SimpleArticleItem(id, imageUrls, title, date, readTimes);
			articles.add(article);
		}
		return articles;
	}

	public int insertArticle(String tableName, ArticleItem article) throws SQLException {
		// the mysql insert statement
		String query = " insert ignore into " + tableName
				+ " (id, image_urls, title, publish_date, read_times,source,body)" + " values (?, ?, ?, ?, ?,?,?)";

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

	/**
	 * 判断某个栏目对应的table是否空
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	public boolean isTableEmpty(int type) throws SQLException {
		String tableName = TableName.getTableByType(type);

		String query = "select count(*) from " + tableName;

		PreparedStatement preparedStmt = connection.prepareStatement(query);

		ResultSet rs = preparedStmt.executeQuery();

		if (rs.next()) {
			if (rs.getInt(1) > 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 获取某个表中的最小 id，也就是最新的新闻 id
	 * @param type
	 * @return
	 * @throws SQLException 
	 */
	public int getMinId(int type) throws SQLException {

		String tableName = TableName.getTableByType(type);
		// 取出最新的新闻 id 表名不能用PreparedStatement
		String query = "select min(id) from " + tableName;
		// create the mysql preparedstatement
		PreparedStatement preparedStmt = connection.prepareStatement(query);

		ResultSet rs = preparedStmt.executeQuery();
		// 空记录 null 会返回 0
		if (rs.next()) {
			return rs.getInt(1);
		}
		// 如果数据库没最大的 id，返回 -1
		return -1;
	}

	/**
	 * 获取某个表中的最大 id，也就是最新的新闻 id
	 * @param type
	 * @return
	 * @throws SQLException 
	 */
	public int getMaxId(int type) throws SQLException {

		String tableName = TableName.getTableByType(type);
		// 取出最新的新闻 id 表名不能用PreparedStatement
		String query = "select max(id) from " + tableName;
		// create the mysql preparedstatement
		PreparedStatement preparedStmt = connection.prepareStatement(query);

		ResultSet rs = preparedStmt.executeQuery();
		// 空记录 null 会返回 0
		if (rs.next()) {
			return rs.getInt(1);
		}
		// 如果数据库没最大的 id，返回 -1
		return -1;
	}

	/**
	 * 判断某个记录是否存在
	 * @param type
	 * @return
	 * @throws SQLException 
	 */
	public boolean isIdExist(int type, int id) throws SQLException {

		String tableName = TableName.getTableByType(type);
		// 取出最新的新闻 id 表名不能用PreparedStatement

		String query = "select exists(select 1 from " + tableName + " where id =?)";
		// create the mysql preparedstatement
		PreparedStatement preparedStmt = connection.prepareStatement(query);
		preparedStmt.setInt(1, id);
		ResultSet rs = preparedStmt.executeQuery();
		// 空记录 null 会返回 0
		if (rs.next()) {
			if (rs.getInt(1) == 1)
				return true;
			else
				return false;
		}
		// 如果数据库没最大的 id，返回 -1
		return false;
	}
}