package com.chenxb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

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
 *  搜索新闻
 * 根据关键词全文搜索
 * @author tomchen
 *
 */
public class SearchArticle extends HttpServlet {

	private ArticleDao dao;

	public SearchArticle() {
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
		if ( req.getParameter("keyword") == null) {
			out.write("usage:http://localhost:8080/test/searchArticle?keyword=电院");
			return;
		}

		// 获取哪个栏目的表
		String word =  req.getParameter("keyword");


		try {
			out.write(word);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
