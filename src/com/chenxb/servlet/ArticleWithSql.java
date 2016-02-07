package com.chenxb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

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
import com.sina.sae.util.SaeUserInfo;

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

		int id = Integer.parseInt(req.getParameter("id"));

		if (req.getParameter("action").equals("add")) {

			try {
				ArticleItem article = ArticleBiz.parseNewsItem(id);
				dao.insertArticle(TableName.LATEST, article);
				out.write("add article " + id);
				out.write(article.toString());
			} catch (Exception e) {
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				out.print(errors.toString());
				e.printStackTrace();
			}

		} else if (req.getParameter("action").equals("query")) {
			ArticleItem article;
			try {
				article = dao.getArticleById(id);
				Gson gson = new GsonBuilder().disableHtmlEscaping().create();
				String result = gson.toJson(article);
				out.write(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
