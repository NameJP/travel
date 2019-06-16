<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSTL的core标签库 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>产品类型管理</title>
<link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>"/>

<link rel="stylesheet" href="<c:url value="/webjars/zTree/3.5.28/css/zTreeStyle/zTreeStyle.css"/>"/>

<script type="text/javascript" src="<c:url value="/webjars/jquery/1.11.1/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"/>"></script>

<script type="text/javascript" src="<c:url value="/webjars/zTree/3.5.28/js/jquery.ztree.all.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/commons.js"/>"></script>
<style type="text/css">
	.product-type-container
	{
		height: 400px;
		overflow: auto;
	}
	.ztree li span.button.add {
		margin-left:2px; 
		margin-right: -1px; 
		background-position:-144px 0; 
		vertical-align:top; 
		*vertical-align:middle
	}
</style>
</head>
<body>
	<div>
		欢迎 ${sessionScope.user.name } 登录，<a href="/security/logout">退出登录</a>
	</div>
	<div class="container-fluid">
		<!-- 左边是树型结构 -->
		<div class="col-md-4 well product-type-container">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
		
		<!-- 右边是信息编辑表单 -->
		<div class="col-md-8">
			<form class="form-horizontal" method="post" id="typeForm">
               <div class="well tree-container">
                   <div class="modal-header">
                       <h4 class="modal-title">产品类型信息</h4>
                   </div>
                   <div class="modal-body">
                       <div class="form-group">
                           <label class="col-sm-2 control-label">上级类型名称</label>
                           <div class="col-sm-10">
                               <p class="parent-type-name form-control-static">无</p>
                               <input name="parent.id" type="hidden"/>
                               <input name="id" type="hidden"/>
                           </div>
                       </div>
                       <div class="form-group">
                           <label for="inputName" class="col-sm-2 control-label">类型名称</label>
                           <div class="col-sm-10">
                               <input class="form-control" id="inputName" placeholder="类型名称" name="name"/>
                           </div>
                       </div>
                   </div>
                   <div class="modal-footer">
                       <button type="submit" class="btn btn-primary">保存</button>
                   </div>
               </div>
           </form>
		</div>
	</div>
	<script type="text/javascript">
	function beforeRemove(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		zTree.selectNode(treeNode);
		// 如果确定删除，就要发送AJAX请求到服务器中，把对应的节点删除掉。
		// 删除成功以后，函数才返回true，否则返回false。
		// 如果返回true，那么节点就会从浏览器页面中删除；返回false，不会删除节点！
		// 需要通过AJAX发送同步请求，默认情况下都是发送异步请求的。
		
		
		fk.confirm("确认删除 节点 -- " + treeNode.name + " 吗？", function(){
			// 执行AJAX删除
			if(treeNode.id){
				$.ajax({
					url: "<c:url value="/admin/productType/"/>" + treeNode.id,
					method: "delete",
					success: function(result){
						if(result.status === 1){
							// 删除成功，把节点从树中删除
							zTree.removeNode(treeNode);
						}else{
							fk.alert("删除失败：" + result.message);
						}
					},
					error: function(result){
						fk.alert("删除失败");
					}
				});
			}
		});
		// 一直返回false，由开发者自己来确定删除的操作！
		return false;
	}
	function onRemove(e, treeId, treeNode) {
	}
	
	// 添加按钮的标记
	var addFlag = true;
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (
				// 如果正则编辑
				treeNode.editNameFlag
				// 或者节点中，已经有addBtn开头的id的元素
				|| $("#addBtn_"+treeNode.tId).length>0){
			// 直接返回、不要显示自定义按钮
			return;
		}
		if( !addFlag )
		{
			// 当没有点击添加按钮的时候，继续往下执行
			// 点击以后，addFlag就变成false，返回、不要显示添加按钮
			return;
		}
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='新增类型' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn){
			btn.bind("click", function(){
				addFlag = false;
				var zTree = $.fn.zTree.getZTreeObj(treeId);
				
				// 添加节点
				zTree.addNodes(treeNode, {pId:treeNode.id, name:"新类型"});
				
				// 添加以后，删除添加按钮
				removeHoverDom(treeId, treeNode);
				return false;
			});
		}
	};
	// 删除添加按钮
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_"+treeNode.tId).unbind().remove();
	};
	
	// 选中节点以后要执行的代码
	var selectNode = function(treeId, treeNode){
		var id = treeNode.id;
		var name = treeNode.name;
		var parent = treeNode.getParentNode();
		
		if( parent != null ){
			var parentId = parent.id;
			var parentName = parent.name;
			
			$("#typeForm input[name='parent.id']").val(parentId);
			$("#typeForm .parent-type-name").text(parentName);
		}else{
			$("#typeForm input[name='parent.id']").val("");
			$("#typeForm .parent-type-name").text("无");
		}
		
		$("#typeForm input[name='id']").val(id);
		$("#typeForm #inputName").val(name);
	};
	
	var setting = {
		view: {
			// 鼠标放到节点上的时候，显示自定义的DOM元素
			addHoverDom: addHoverDom,
			// 当鼠标离开节点时候，把自定义的DOM元素从节点中删除
			removeHoverDom: removeHoverDom,
			// 不允许选择多个
			selectedMulti: false
		},
		edit: {
			// 可以编辑，包括删除、添加、重命名
			enable: true,
			// 编辑名字的时候，选择整个名字
			//editNameSelectAll: true,
			// 显示删除按钮
			showRemoveBtn: true,
			// 显示重命名按钮
			showRenameBtn: false
		},
		data: {
			simpleData: {
				// 不是简单数据，而是JSON数据就该设置为false
				enable: false
			}
		},
		// 回调，一旦涉及到【回调】的，一定是处理【事件】的。
		callback: {
			// 在拖动之前，会调用一个js函数，用于预处理
			//beforeDrag: beforeDrag,
			// 在编辑名称之前，进行预处理
			//beforeEditName: beforeEditName,
			// 在删除节点之前
			beforeRemove: beforeRemove,
			// 在重命名之前
			//beforeRename: beforeRename,
			// 当确定删除节点的时候
			onRemove: onRemove,
			// 当确定重命名的时候
			//onRename: onRename
			
			// 当节点选中以后要执行的代码
			// onSelected在API文档里面是没有相关介绍的，要源代码里面才有
			onSelected: selectNode
		}
	};

	var zNodes = ${requestScope.json};

	$(document).ready(function(){
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	});
	</script>
</body>
</html>