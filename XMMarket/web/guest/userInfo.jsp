﻿<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/guest/style.css" />
<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.form.js"></script>
<style>
.main_iframe {
	Z-INDEX: 1;
	VISIBILITY: inherit;
	WIDTH: 100%;
	HEIGHT: 92%
}
</style>
<script>
	function saveData() {
		var realname = document.getElementById("realname").value;
		if (realname == null || realname == '') {
			alert("真实姓名不能为空！");
			return false;
		} else if (realname.length > 4) {
			alert("真实姓名长度不能大于4！");
			return false;
		}

		var address = document.getElementById("address").value;
		if (address == null || address == '') {
			alert("地址不能为空！");
			return false;
		}

		var phone = document.getElementById("phone").value;
		var phoneReg = /^1[3|4|5|6|7|8|9][0-9]{9}$/;
		if (phone == null || phone == '') {
			alert("电话不能为空！");
			return false;
		} else if (!phoneReg.test(phone)) {
			alert("电话格式错误！");
			return false;
		}

		//提交修改请求
		$("#myform").ajaxSubmit({
			success : function(jsonData) {
				if (jsonData.flag == false) {
					window.alert(jsonData.message);
				} else {
					window.alert(jsonData.message);
					window.location.reload();
				}
			},
			error : function(jsonData) {
				window.alert("加载失败");
			},
			async : true,//异步
			url : "${contextPath}/servlet/RegServlet?task=modifySelf&uid=${regUserBean.uid}" + "&date=" + Math.random() + "",
			type : "post", /*设置表单以post方法提交*/
			dataType : "json" /*设置返回值类型为文本*/
		});
	}
</script>
<script>
	function fun(t) {
		t.style.display = '';
	}
	function show(t) {
		t.style.display = 'none';
	}
</script>
</head>
<body>

	<div id="main_container" style="height: 384px; width: 580px; background-color: #E9E7E7; float: left;">
		<div id="main_content">
			<table border="0" bgcolor="#e9e7e7" align="center" width=100%>
				<tr>
					<td class="center_content" width=100% style="height: 380px;">
						<c:if test="${empty regUserBean}">
							<table bgcolor="#e9e7e7" width=100% align=center>

								<tr>
									<td align=center>
										<h3>对不起，还未登录，请登录</h3>
										<h3>
											正在跳转到登录页面，若没有跳转请
											<a href="${contextPath}/guest/login.jsp">点击这里！</a>
										</h3>
									</td>
								</tr>
							</table>
							<%
								response.setHeader("refresh", "3;url=login.jsp");
							%>
						</c:if>
						<c:if test="${!empty regUserBean}">
							<form method="post" action="#" onsubmit="return check();" id="myform" enctype="multipart/form-data">

								<table width=500 border="0" align="center" bgcolor="#e9e7e7">
									<tr>
										<td colspan=3 bgcolor="#c0c0c0" align="center">
											<h3>用户信息更新</h3>
										</td>
									</tr>
									<tr>
										<td width=60 height=30>用户名:</td>
										<td width=100>
											<input type="hidden" name="uid" value="${regUserBean.uid}" />
											<input id="username" type="text" name="username" size="20" value="${regUserBean.username}" readonly="readonly" />
										</td>
										<td width=220></td>
									</tr>

									<tr>
										<td height=30>真实姓名:</td>
										<td width=100>
											<input id="realname" type="text" name="realname" size="20" onclick="fun(msg3)" onblur="show(msg3)" value="${regUserBean.realname}" />
										</td>
										<td width=180>
											<div id=msg3 style="color: #ff0000; display: none;">输入您的真实姓名</div>
										</td>
									</tr>

									<tr>
										<td height=30>联系电话:</td>
										<td width=100>
											<input id="phone" type="text" name="phone" size="20" value="${regUserBean.phone}" onclick="fun(msg4)" onblur="show(msg4)" />
										</td>
										<td width=180>
											<div id=msg4 style="color: #ff0000; display: none;">输入您联系电话</div>
										</td>
									</tr>

									<tr>
										<td height=30>收货地址:</td>
										<td width=100>
											<input id="address" type="text" name="address" size="20" value="${regUserBean.address}" onclick="fun(msg5)" onblur="show(msg5)" />
										</td>
										<td width=180>
											<div id=msg5 style="color: #ff0000; display: none;">输入您的收货地址，请认真填写</div>
										</td>
									</tr>

									<tr>
										<td></td>
										<td>
											<input type="button" name="submit1" value="提交" onclick="saveData();" />
											<input type="button" name="Submit2" onclick="javascript:history.back(-1);" value="返回" />
										</td>
									</tr>

								</table>
							</form>
						</c:if>
					</td>
					<td width=20%></td>
				</tr>
			</table>
		</div>
</body>
</html>
