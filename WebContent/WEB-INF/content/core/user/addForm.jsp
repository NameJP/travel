<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <div class="modal-dialog" role="document">
        <div class="modal-content">
        	<form action="" method="post" class="form-horizontal">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                	<span aria-hidden="true">&times;</span>
	               	</button>
	                <h4 class="modal-title" id="myModalLabel">添加用户</h4>
	            </div>
	            <div class="modal-body">
	            	<div class="form-group">
					    <label for="inputName" class="col-sm-2 control-label">登录名</label>
					    <div class="col-sm-10">
					        <input class="form-control" id="inputName" placeholder="请输入登录名" name="name" value="${param.name }"/>
					        <input type="hidden" name="id" id="id"/>
					    </div>
					</div>
					<div class="form-group">
					    <label for="inputPassword" class="col-sm-2 control-label">密码</label>
					    <div class="col-sm-10">
					        <input type="password" class="form-control" id="inputPassword" placeholder="输入密码" name="password"/>
					    </div>
					</div>
	            	<div class="form-group">
					    <label for="inputEmail" class="col-sm-2 control-label">电子邮箱</label>
					    <div class="col-sm-10">
					        <input class="form-control" id="inputEmail" placeholder="请输入电子邮箱地址" name="email" value="${param.email }"/>
					    </div>
					</div>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	                <button type="submit" class="btn btn-primary">保存</button>
	            </div>
            </form>
        </div>
    </div>