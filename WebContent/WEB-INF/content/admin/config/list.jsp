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
	<div>
		欢迎 ${sessionScope.user.name } 登录，<a href="/security/logout">退出登录</a>
	</div>
	<div class="">
		<div class="col-md-12" >
			<div style="text-align: right;">
				<a class="btn btn-primary btn-sm" data-toggle="modal" data-target="#configDialog">添加</a>
			</div>
		</div>
		<div class="col-md-12">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>参数名</th>
						<th>值</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<%-- 使用C标签库循环page对象里面的数据 --%>
					<c:forEach items="${page.content }" var="kv">
					<tr>
						<td>${kv.key }
							<input name="id" value="${kv.id }" type="hidden"/>
							<input name="key" value="${kv.key }" type="hidden"/>
							<input name="value" value="${kv.value }" type="hidden"/>
						</td>
						<td>${kv.value }</td>
						<td>
							<a class="btn btn-success updateButton">修改</a>
							<a class="btn btn-default deleteButton">删除</a>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			<div></div>
		</div>
	</div>
	<div class="modal fade" tabindex="-1" role="dialog" id="configDialog">
		<div class="modal-dialog" role="document">
		    <div class="modal-content">
		        <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		            	<span aria-hidden="true">&times;</span>
	            	</button>
		            <h4 class="modal-title">系统参数设置</h4>
		        </div>
				<form class="form-horizontal" action="" method="post">
			        <div class="modal-body">
						    <div class="form-group">
						        <label for="inputKey" class="col-sm-2 control-label">参数名称</label>
						        <div class="col-sm-10">
						            <input class="form-control" id="inputKey" placeholder="KEY，给程序中使用的" name="key" />
						            <input type="hidden" name="id" value=""/>
						        </div>
						    </div>
						    <div class="form-group">
						        <label for="inputValue" class="col-sm-2 control-label">参数值</label>
						        <div class="col-sm-10">
						            <input class="form-control" id="inputValue" placeholder="参数实际的值" name="value" />
						        </div>
						    </div>
			        </div>
			        <div class="modal-footer">
			            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			            <button type="submit" class="btn btn-primary">保存</button>
			        </div>
				</form>
		    </div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div>
	
	<script type="text/javascript">
	$(".deleteButton").click(function(){
		var url = "<c:url value='/admin/config'/>";
		var row = $(this).parent().parent();
		//console.log(row);
		var id = $("input[name='id']", row).val();
		$.ajax({
			url: url + "/" + id,
			method: "delete",
			success: function(){
				document.location.href=url;
			},
			error: function(resp){
				alert(resp);
			}
		});
	});
	
	$(".updateButton").click(function(){
		var row = $(this).parent().parent();
		var id = $("input[name='id']", row).val();
		var key = $("input[name='key']", row).val();
		var value = $("input[name='value']", row).val();
		$("#configDialog [name='id']").val(id);
		$("#configDialog #inputKey").val(key);
		$("#configDialog #inputValue").val(value);
		
		// 显示对话框
		$('#configDialog').modal("show");
	});
	</script>
</body>
</html>