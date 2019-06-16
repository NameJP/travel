<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSTL的core标签库 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>系统参数设置</title>
<link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>"/>
<script type="text/javascript" src="<c:url value="/webjars/jquery/1.11.1/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"/>"></script>
</head>
<body>
	<div class="contianer-fluid">
		<div class="modal-dialog" role="document">
	        <div class="modal-content">
	            <form class="form-horizontal" method="post">
		            <div class="modal-header">
		                <h4 class="modal-title">欢迎登录</h4>
		            </div>
		            <div class="modal-body">
		            	<c:if test="${not empty msg }">
		            		<div class="alert alert-danger" role="alert">${msg }</div>
		            	</c:if>
					    <div class="form-group">
					        <label for="inputName" class="col-sm-2 control-label">登录名</label>
					        <div class="col-sm-10">
					            <input name="name" class="form-control" id="inputName" placeholder="请输入登录名" />
					        </div>
					    </div>
					    <div class="form-group">
					        <label for="inputPassword" class="col-sm-2 control-label">密码</label>
					        <div class="col-sm-10">
					            <input type="password" class="form-control" id="inputPassword" placeholder="请输入登录名密码" name="password" />
					        </div>
					    </div>
		            </div>
		            <div class="modal-footer">
		                <button type="submit" class="btn btn-primary">登录</button>
		            </div>
				</form>
	        </div>
	    </div>
	</div>
</body>
</html>