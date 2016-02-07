package com.chenxb.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chenxb.biz.ArticleBiz;
import com.chenxb.model.ArticleItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 使用示例 http://localhost:8080/seenews/parseArticle?id=7938
 * 根据给定的id从电院http://see.xidian.edu.cn/html/news/7938.html爬取数据 返回 json 字符串
 * 
 * @author tomchen
 *
 */
public class ParseArticleById extends HttpServlet {

	private static final long serialVersionUID = 1L;

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

		int id = Integer.parseInt(req.getParameter("id"));

		PrintWriter out = resp.getWriter();
		try {
			ArticleItem article = ArticleBiz.parseNewsItem(id);
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			String result = gson.toJson(article);
			out.write(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}

}
