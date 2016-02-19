package com.chenxb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chenxb.biz.ArticleBiz;
import com.chenxb.dao.ArticleDao;
import com.chenxb.model.ArticleItem;
import com.chenxb.util.TableName;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 从 mysql 中根据 id 和 column 获取新闻详情
 * 先获取 colunm，再到对应的表里查询数据
 * @author tomchen
 *
 */
public class ArticleWithSql extends HttpServlet {

	private ArticleDao dao;

	public ArticleWithSql() {
		super();
		try {
			dao = new ArticleDao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 目的是为了控制浏览器的行为，即控制浏览器用UTF-8进行解码；
		resp.setContentType("text/html;charset=UTF-8");

		// 用于response.getWriter()输出的字符流的乱码问题，response.getOutputStream()是不需要此种解决方案的
		// 因为这句话的意思是为了将response对象中的数据以UTF-8解码后发向浏览器；
		resp.setCharacterEncoding("UTF-8");

		PrintWriter out = resp.getWriter();
		if (req.getParameter("id") == null || req.getParameter("column") == null) {
			out.write("usage: http://localhost:8080/test/articleWithSql?column=1&id=7000");
			return;
		}

		try {
			if (dao == null || dao.getConnection().isClosed()) {
				out.write("mysql is null or closed\n");
				return;
			}
		} catch (SQLException e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			out.write("mysql is null or closed\n");
			out.print(errors.toString());
		}

		// 获取哪个栏目的表
		int type = Integer.parseInt(req.getParameter("column"));

		int id = Integer.parseInt(req.getParameter("id"));

		ArticleItem article;
		try {
			article = dao.getArticleByTypeId(type, id);
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			String result = gson.toJson(article);
			out.write(result);
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			out.write("ArticleDao getArticleByTypeId error\n");
			out.print(errors.toString());
		} finally {
			out.flush();
			out.close();
		}
	}

}
