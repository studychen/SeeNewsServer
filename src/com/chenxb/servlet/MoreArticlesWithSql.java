package com.chenxb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chenxb.dao.ColumnDao;
import com.chenxb.model.SimpleArticleItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 某栏目，查找大于某个id的新数据
 * 分页返回
 * @author tomchen
 *
 */
public class MoreArticlesWithSql extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ColumnDao colDao;

	public MoreArticlesWithSql() {
		super();
		try {
			colDao = new ColumnDao();
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

		if (req.getParameter("column") == null || req.getParameter("morethan") == null) {
			out.write("usage: http://localhost:8080/test/columnWithSql?column=1&morethan=7916");
			return;
		}

		int type = Integer.parseInt(req.getParameter("column"));
		int morethan = Integer.parseInt(req.getParameter("morethan"));

		// 下面几页就是根据偏移量
		try {
			List<SimpleArticleItem> articles = colDao.moreArticles(type, morethan);
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			String result = gson.toJson(articles);
			out.write(result);
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			out.write("ColumnDao getTopSimpleArticles error\n");
			out.print(errors.toString());
		} finally {
			out.flush();
			out.close();
		}
	}

}
