package com.chenxb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chenxb.dao.RotationImageDao;
import com.chenxb.model.RotationItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RotationWithSql extends HttpServlet {

	private RotationImageDao dao;

	public RotationWithSql() {
		super();
		try {
			dao = new RotationImageDao();
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

		try {
			List<RotationItem> rotations = dao.getTopRotations();
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			String result = gson.toJson(rotations);
			out.write(result);

		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			out.print(errors.toString());
			e.printStackTrace();
		}

	}

}
