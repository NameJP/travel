<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSTL的core标签库 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>产品管理列表</title>
<link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>"/>
<script type="text/javascript" src="<c:url value="/webjars/jquery/1.11.1/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"/>"></script>
</head>
<body>
	<div>
		欢迎 ${sessionScope.user.name } 登录，<a href="/security/logout">退出登录</a>
	</div>
	<div class="">
		<div class="col-md-12" >
			<div style="text-align: right;">
				<a class="btn btn-primary btn-sm" href="<c:url value="/admin/product/add"/>">添加</a>
			</div>
		</div>
		<div class="col-md-12">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
					</tr>
				</thead>
				<tbody>
					<tr>
					</tr>
				</tbody>
			</table>
			<div></div>
		</div>
	</div>
	
</body>
</html>