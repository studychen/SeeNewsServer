<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.chenxb.news.*"%>
<%@ page language="java" import="com.chenxb.model.*"%>
<%@ page language="java" import="com.chenxb.biz.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String num = request.getParameter("num");
	String original = "http://see.xidian.edu.cn/html/news/" + num + ".html";
	ArticleItem detail = ArticleBiz.parseNewsItem(Integer.parseInt(num));
	request.setAttribute("detail", detail);
	request.setAttribute("original", original);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=2.0, minimum-scale=1.0, user-scalable=yes" />
<%--显示为可以拨号的连接 --%>
<meta name="format-detection" content="telephone=yes">

<title>${detail.title }</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" type="text/css" href=./css/detail.css>
</head>

<body>
	<div id="article_title">
		<h1>${detail.title }</h1>
	</div>

	<div id="article_detail">
		<span>${detail.publishDate } </span> <span>
			浏览次数:${detail.readTimes }</span>
	</div>

	<div id="article_content">${detail.body }</div>

	<div id="original_post">
		<p>
			SeeNews已优化<a href="${original}">原网页</a>方便移动设备查看
		</p>
	</div>

</body>
</html>
