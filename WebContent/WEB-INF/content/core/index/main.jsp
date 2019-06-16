<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>首页</title>
<link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>"/>
<script type="text/javascript" src="<c:url value="/webjars/jquery/1.11.1/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"/>"></script>
<style type="text/css">
.banner-nav
{
	background-color: green;
}
.banner-nav ul
{
	list-style: none;
	margin-bottom: 0px;
}
.banner-nav ul li
{
	display: inline-block;
	padding-top: 10px;
	padding-bottom: 4px;
	padding-left: 10px;
	padding-right: 10px;
}
.banner-nav ul li a
{
	color: white;
	font-size: 20px;
}
.banner-nav ul li:hover
{
	background-color: #50ff50;
}
.banner-nav ul li a:hover
{
	text-decoration: none; 
	cursor: pointer;
}

</style>
</head>
<body class="container-fluid">
	<!-- 横幅 -->
	<div class="col-md-12">
		<div class="col-md-12">
			<c:choose>
				<%-- 如果已经登录，显示用户信息、退出按钮 --%>
				<c:when test="${not empty sessionScope.user }">
					欢迎 ${sessionScope.user.name } ，<a href="<c:url value="/security/logout"/>">退出登录</a>
				</c:when>
				<%-- 否则显示登录、注册按钮 --%>
				<c:otherwise>
					<a href="<c:url value="/security/login"/>">登录</a>
					<a href="<c:url value="/security/register"/>">注册</a>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="col-md-12">
			<div class="col-md-3">
				<img src="<c:url value="/resources/image/logo.png"/>"/>
			</div>
			<div class="col-md-5">
				<div class="input-group" style="margin-top: 30px;">
				    <div class="input-group-addon">搜索</div>
				    <input type="text" class="form-control" placeholder="夏天最好玩的地方--广州" />
				    <div class="input-group-addon"><span class="glyphicon glyphicon-search"></span></div>
				</div>
			</div>
		</div>
		<div class="col-md-12 banner-nav">
			<ul class="">
				<li>
					<a href="<c:url value="/core/index"/>">首页</a>
				</li>
				<c:forEach items="${indexPage.topTypes }"  var="t">
				<li>
					<a href="<c:url value="/core/search?typeId=${t.id }"/>">${t.name }</a>
				</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	
	<!-- 首屏广告 -->
	<div class="">
	</div>
	
	<!-- 如果后面还有其他内容，继续加！ -->
</body>
</html>