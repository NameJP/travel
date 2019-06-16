<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- JSTL的core标签库 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>添加产品信息</title>
<link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>"/>

<link rel="stylesheet" href="<c:url value="/webjars/zTree/3.5.28/css/zTreeStyle/zTreeStyle.css"/>"/>

<link rel="stylesheet" href="<c:url value="/resources/wangEditor/wangEditor.min.css"/>"/>

<link rel="stylesheet" href="<c:url value="/webjars/fullcalendar/3.9.0/dist/fullcalendar.min.css"/>"/>
<link rel="stylesheet" href="<c:url value="/webjars/fullcalendar/3.9.0/dist/fullcalendar.print.min.css"/>" media='print'/>

<script type="text/javascript" src="<c:url value="/webjars/jquery/1.11.1/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"/>"></script>

<script type="text/javascript" src="<c:url value="/webjars/zTree/3.5.28/js/jquery.ztree.all.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/commons.js"/>"></script>

<!-- 支持异步表单的jQuery插件 -->
<script type="text/javascript" src="<c:url value="/resources/form-3.51/jquery.form.js"/>"></script>

<script type="text/javascript" src="<c:url value="/resources/wangEditor/wangEditor.min.js"/>"></script>

<script type="text/javascript" src="<c:url value="/webjars/moment/2.22.2/min/moment.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/webjars/fullcalendar/3.9.0/dist/fullcalendar.js"/>"></script>
<script type="text/javascript" src="<c:url value="/webjars/fullcalendar/3.9.0/dist/locale/zh-cn.js"/>"></script>
<style type="text/css">
#images img
{
	width: 50px;
	height: 50px;
}
</style>
</head>
<body>
	<div>
		欢迎 ${sessionScope.user.name } 登录，<a href="/security/logout">退出登录</a>
	</div>
	<!-- 显示的页面布局、主要数据填写区 -->
	<div class="container-fluid">
		<form action="<c:url value="/admin/product"/>" class="form-horizontal" method="post"
			onsubmit="return checkData();">
			<div class="form-group">
				<label for="selectType" class="col-sm-2 control-label">选择产品类型</label>
				<div class="col-sm-10">
					<div class="input-group">
						<input class="form-control" id="typeName" readonly="readonly" required="required" />
						<div class="input-group-addon">
							<span class="glyphicon glyphicon-check" id="selectType" style="cursor: pointer;"></span>
						</div>
					</div>
					<input type="hidden" name="type.id" id="typeId" value=""/>
				</div>
			</div>
			<div class="form-group">
				<label for="inputName" class="col-sm-2 control-label">名称</label>
				<div class="col-sm-10">
					<input class="form-control" id="inputName" name="name" required="required" />
				</div>
			</div>
			<div class="form-group">
				<label for="inputPrice" class="col-sm-2 control-label">价格</label>
				<div class="col-sm-10">
					<input class="form-control" id="inputPrice" name="price" required="required" />
				</div>
			</div>
			<div class="form-group">
				<label for="selectType" class="col-sm-2 control-label">选择产品类型</label>
				<div class="col-sm-10">
					<!-- 上传以后，用于显示图片的容器 -->
					<div id="images">
					</div>
					<!-- 该input不会上传，而是把文件的信息，复制到另外一个表单、采用异步上传方式来实现！ -->
					<input type="file" id="uploadImage" accept="image/*"/>
				</div>
			</div>
			<div class="form-group">
				<label for="selectType" class="col-sm-2 control-label">产品描述</label>
				<div class="col-sm-10">
					<div id="productDescription"></div>
					<input type="hidden" name="description"/>
 				</div>
			</div>
			<div class="form-group">
				<label for="selectType" class="col-sm-2 control-label">路线介绍</label>
				<div class="col-sm-10">
					<div id="lineIntraduction"></div>
					<input type="hidden" name="intraduction"/>
				</div>
			</div>
			<div class="form-group">
				<label for="selectType" class="col-sm-2 control-label">费用说明</label>
				<div class="col-sm-10">
					<div id="costDescription"></div>
					<input type="hidden" name="cost"/>
				</div>
			</div>
			<div class="form-group">
			    <label for="inputStartOutDate" class="col-sm-2 control-label">出行计划</label>
			    <div class="col-sm-10">
			        <div id='start-out-calendar'></div>
			        <span id="startOutSpan" style="display: none;"></span>
			    </div>
			</div>
			<div class="form-group">
				<div class="offset-col-md-4 col-sm-4">
					<button type="submit">提交保存</button>
				</div>
			</div>
		</form>
	</div>
	
	<!-- 隐藏的内容 -->
	<!-- 产品类型树显示的DIV -->
	<!-- 当点击【选择】按钮的时候，把下面的div显示在id为typeName的输入框下面 -->
	<div style="z-index:10002; display: none; position: absolute; background-color: white; border: 1px solid #eee;" id="typeTreeContainer">
		<div style="height: 200px; overflow: auto;">
			<ul id="typeTree" class="ztree"></ul>
		</div>
		<div>
			<a class="btn btn-primary" id="selectedType">确定</a>
			<a class="btn btn-default" id="cancelSelectType">取消</a>
		</div>
	</div>
	
	<div style="display: none;" id="uploadImageDiv">
		<form action="<c:url value="/commons/file"/>" method="post" enctype="multipart/form-data">
			<input name="file" type="file"/>
		</form>
	</div>
	
	<!-- JS代码 -->
	<script type="text/javascript">
	var setting = {};
	var zNodes = ${requestScope.json};
	$(document).ready(function(){
		$.fn.zTree.init($("#typeTree"), setting, zNodes);
		
		// 绑定【选择】按钮的点击事件
		$("#selectType").bind("click", function(){
			$("#typeTreeContainer").show();
			var top = $("#typeName").offset().top;
			var left= $("#typeName").offset().left;
			var heigth = $("#typeName").height() + 12;
			$("#typeTreeContainer").offset({ top: top + heigth, left: left });
			$("#typeTreeContainer").width($("#typeName").width() + 24);
		});
		
		// 绑定取消按钮的事件
		$("#cancelSelectType").bind("click", function(){
			$("#typeTreeContainer").hide();
		});
		$("#selectedType").bind("click", function(){
			$("#typeTreeContainer").hide();
			
			// 获取选中的节点的值，把id和name取出来，放入表单。
			var zTree = $.fn.zTree.getZTreeObj("typeTree");
			var nodes = zTree.getSelectedNodes();
			if(nodes.length > 0){
				var node = nodes[0];
				$("#typeId").val(node.id);
				$("#typeName").val(node.name);
			}
		});
		
		// 处理异步文件上传功能
		$("#uploadImage").bind("change", function(){
			// 把异步上传表单里面的内容，清空！
			$("#uploadImageDiv form").html("");
			// 把选择文件的输入框，克隆一份放到异步上传表单里面
			$("#uploadImage").clone().appendTo($("#uploadImageDiv form"));
			// 设置异步上传表单里面，input的name属性
			$("#uploadImageDiv form input").attr("name", "file");
			
			// 异步上传选项
			var options = {
				// 上传成功以后执行的函数
                success: function (data, statusText) {
                    if (data.status === 1)
                    {
                    	// 上传成功以后，把图片显示在页面上
                        var url = "${pageContext.request.contextPath}/commons/file/" + data.attachment;
                        var img = "<span><img src='" + url + "'/><input type='hidden' name='imageIds' value='" + data.attachment + "'/></span>";
                        $(img).appendTo($("#images"));
                    } else {
                        fk.alert("图片上传失败");
                    }
                },
                // 出错的时候执行的回调
                error: function (data) {
                    fk.alert("错误：" + data.responseText);
                },
                // 上传完成以后，清空图片选择的输入框
                complete: function () {
                    $("#uploadImage").val("");
                },
                // 返回的数据类型要求使用json格式的
                dataType: 'json',
                // 上传图片的URL
                //url: "[[@{/commons/file}]]",
                type: "POST"
            };
			//执行异步上传 
            $("#uploadImageDiv form").ajaxSubmit(options);
		});
		
		// 创建富文本编辑器
		var createEditor = function( id, name ){
	        var E = window.wangEditor;
	        var editor = new E('#' + id);
	        
	        // 获取编辑器的值，放入表单，方便提交
	        editor.customConfig.onchange = function (html) {
	            // html 即变化之后的内容
	            // console.log(html)
	            $("input[name='"+name+"']").val(html);
	        };
	        
	        // 上传文件配置
	        editor.customConfig.uploadImgServer = '<c:url value="/commons/file/wang"/>';
	        editor.customConfig.uploadFileName = 'file';
	        
	        // 或者 var editor = new E( document.getElementById('#editor') )
	        editor.create();
	        
		};
		
		createEditor("productDescription", "description");
		createEditor("lineIntraduction", "intraduction");
		createEditor("costDescription", "cost");
		
		
		// 处理日历相关的逻辑
        var startOutIndex = 0;
        $('#start-out-calendar').fullCalendar({
        	// 日历的头部要显示哪些按钮
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay,listMonth'
            },
            //defaultDate: '2018-03-12',
            locale: 'zh-cn', // 显示的语言
            buttonIcons: true, // 是否显示上月、下月的按钮
            weekNumbers: false, // 是否显示第几周
            navLinks: false, // can click day/week names to navigate views
            editable: true, // 是否可编辑
            eventLimit: true, // allow "more" link when too many events
            // 最开始显示在日历中的事件
            events: [
            ],
            selectable: true,
            selectHelper: true,
            select: function (start, end) {// 当选中某个日期的时候触发事件处理
            	// 弹出对话框，用于输入城市
                var location = prompt('请输入出发地名（城市、区域）:');
                var eventData;
                if (location) {
                    // 记录出发时间、出发位置，用于记录到数据库
                    var date = moment(start).format("YYYY-MM-DD");
                    var tmp = "<span id='startOut" + startOutIndex + "' class='startOut'>\
                        <input type='hidden' id='startOutDate_" + startOutIndex + "' name='startOuts[0].date' value='" + date + "'/>\n\
                        <input type='hidden' id='startOutCity_" + startOutIndex + "' name='startOuts[0].location' value='" + location + "'/></span>";
                    $(tmp).appendTo($("#startOutSpan"));

                    var eventData = {
                        title: location,
                        start: start,
                        startOutIndex: startOutIndex
                    };
                    startOutIndex++;
                    $('#start-out-calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
                }
                $('#start-out-calendar').fullCalendar('unselect');
            },
            // 当点击某个事件的时候，触发删除操作
            eventClick: function (calEvent, jsEvent, view) {
                if (confirm("确定要删除此计划吗？")) {
                    $('#start-out-calendar').fullCalendar("removeEvents", calEvent._id);
                    $("#startOutSpan #startOut" + calEvent.startOutIndex).remove();
                }
            },
            eventMouseover: function (event, jsEvent, view) {
            },
            eventMouseout: function (event, jsEvent, view) {
            }
        });
	});
	
	// 提交表单的时候，用来检查、整理表单数据，特别是集合中的数据！
	var checkData = function(){
		// 获取隐藏的span元素
		var spans = $(".startOut");
		
		// 遍历得到的所有span
        spans.each(function (index, ele) {
            var span = $(this);// 获取当前的span
            
            // 获取span里面的input
            var inputs = $("input", span);
            inputs.each(function (i, input) {
            	
            	// 获取input的name属性的值
                var name = $(input).attr("name");
            	
            	// 把中括号里面的数字，替换成span的索引
            	// startOuts[0].date => startOuts[0].date、startOuts[1].date
            	// /\[\d+\]/
                name = name.replace(/\[\d+\]/, "[" + index + "]");
            	
            	// 修改name属性的值
                $(input).attr("name", name);
            });
        });
        return true;
	};
	
	</script>
</body>
</html>