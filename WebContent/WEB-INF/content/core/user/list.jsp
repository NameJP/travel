<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户管理</title>
<link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>"/>
<script type="text/javascript" src="<c:url value="/webjars/jquery/1.11.1/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"/>"></script>
</head>
<body>
	<a class="btn btn-default" data-target="#addUserDialog" data-toggle="modal" >添加</a>
	<table class="table table-hover">
		<thead>
			<tr>
				<th>用户名</th>
				<th>邮件地址</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.content }" var="u">
			<tr>
				<td>${u.name }</td>
				<td>${u.email }</td>
				<td>
					<input type="hidden" name="id" value="${u.id }"/>
					<input type="hidden" name="name" value="${u.name }"/>
					<input type="hidden" name="email" value="${u.email }"/>
					<a class="btn btn-xs btn-success edit-button">修改</a>
					<a class="btn btn-xs btn-danger delete-button">删除</a>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>

	<div>
		<nav aria-label="Page navigation">
		    <ul class="pagination">
		        <li>
		            <a href="?pageNumber=${page.number eq 0 ? 0 : page.number - 1 }" aria-label="上一页">
		            	<span aria-hidden="true">上一页</span>
		            </a>
		        </li>
		        <li>
		        	<%-- 页码从0开始，但是总页数从1开始 --%>
		            <a href="?pageNumber=${page.number eq page.totalPages - 1 ? page.totalPages - 1 : page.number + 1 }" aria-label="下一页">
		            	<span aria-hidden="true">下一页</span>
		            </a>
		        </li>
		    </ul>
		</nav>
	</div>
<div class="modal fade" id="addUserDialog" tabindex="-1" role="dialog" aria-labelledby="添加用户">
    <jsp:include page="/WEB-INF/content/core/user/addForm.jsp"></jsp:include>
</div>

<script type="text/javascript">
$(function(){
	$(".edit-button").click(function(){
		// 获取所有隐藏域的值
		var td = $(this).parent();
		var id = $("input[name='id']", td).val();
		var name = $("input[name='name']", td).val();
		var email = $("input[name='email']", td).val();
		
		// 把获取到的值，放入对话框
		$("#addUserDialog #id").val(id);
		$("#addUserDialog input[name='name']").val(name);
		$("#addUserDialog input[name='email']").val(email);
		
		// 显示对话框
		$('#addUserDialog').modal();
	});
	
	$(".delete-button").click(function(){
		if(confirm("确定要删除用户吗？")){
			var td = $(this).parent();
			var id = $("input[name='id']", td).val();
			
			$.ajax({
				url: "<c:url value="/core/user/"/>" + id,
				method: "delete",// 发送delete请求
				success: function(result){
					if( result.status == 1){
						alert("删除成功");
						// 相当于是JS的重定向
						document.location.href="<c:url value="/core/user/"/>";
					}else{
						alert("删除失败：" + result.message);
					}
				},
				error: function(){
					alert("删除失败");
				}
			});
		}
	});
});
</script>
</body>
</html>