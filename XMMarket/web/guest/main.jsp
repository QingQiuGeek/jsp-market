<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/guest/style.css" />
<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
<style>
.main_iframe {
	Z-INDEX: 1;
	VISIBILITY: inherit;
	WIDTH: 100%;
	HEIGHT: 92%
}
</style>

<script type="text/javascript">
	function searchItemByKeyWord() {
		var keyWord = $("#searchKeyWord").val();
		// 		window.alert("keyWord: "+keyWord);
		var searchForm = document.getElementById("searchForm");
		searchForm.submit();
	}

	function loginSystem() {
		var formData = jQuery("#loginForm").serializeArray();
		var url = "${contextPath}/servlet/LoginServlet?task=loginRegUser&date=" + Math.random() + "";
		jQuery.post(url, formData, function(jsonData) {
			if (jsonData.flag == false) {
				window.alert(jsonData.message);
			} else {
				//					window.alert(jsonData.message);
				var url = "${contextPath}/servlet/MainServlet?task=guestMainPage&date=" + Math.random() + "";
				window.top.location.href = url;
			}
		}, "json");
	}
</script>
</head>
<body>


	<div id="main_container">
		<div class="top_bar"></div>
		<div id="header">
			<div id="divstr">
				<br/>
				<p style="font-size: 50px ;font-weight:lighter;text-align: center;margin-top: auto">X   M   商   城</p>
			</div>
		</div>

		<div id="main_content">
			<div id="menu_tab">
				<div class="left_menu_corner"></div>
				<ul class="menu">
					<li>
						<a href="${contextPath}/servlet/MainServlet?task=guestMainPage" class="nav1">首页</a>
					</li>
					<li class="divider"></li>
					<li>
						<a href="${contextPath}/servlet/MainServlet?task=guestMainPage" class="nav2">在线购物</a>
					</li>
					<li class="divider"></li>
					<li>
						<a href="${contextPath}/servlet/CartServlet?task=list" target="frmright" class="nav5">我的购物车</a>
					</li>
					<li class="divider"></li>
					<form id="searchForm" action="${contextPath}/servlet/MainServlet?task=frmright" target="frmright" method="post">
						<input id="searchKeyWord" name="searchKeyWord" style="font-size: 19pt; color: rgb(85, 85, 85);margin-right: 15px" onfocus="if(this.value=='请输入查找的商品关键字'){this.value='';}" onblur="if(this.value==''){this.value='请输入所要查找的商品关键字';}" placeholder="请输入所要查找的商品关键字" size="25" maxlength="30" type="text" />
						<input style="font-size: large;margin-top: 5px" name="搜索" type="button" value="搜索" onclick="searchItemByKeyWord();" />
					</form>
				</ul>

				<div class="right_menu_corner"></div>
			</div>


		</div>
		</div>
		<table border="0" cellpadding="0" cellspacing="0" align="center" width=100% height=400px>
			<tr>
				<td class="left_content">
					<table>
						<tr>
							<td class="title_box" style="font-size: x-small;font-weight: lighter;">商   品   分   类</td>
						</tr>
						<tr>
							<td>
								<ul class="left_menu">
									<c:forEach var="category" items="${categoryBeanList}">
										<li class="odd">
											<a href="${contextPath}/servlet/MainServlet?task=frmright&maxid=${category.cid}" target="frmright">${category.classname}</a>
										</li>
										<c:forEach var="childcategory" items="${category.childCategory}">
											<li class="even">
												<a href="${contextPath}/servlet/MainServlet?task=frmright&minid=${childcategory.cid}"  target="frmright">—${childcategory.classname}</a>
											</li>
										</c:forEach>
									</c:forEach>
								</ul>
								<br />
							</td>
						</tr>

					</table>
				</td>

				<td class=center_table width=100% height=428px;>
					<iframe class=main_iframe id=frmright name="frmright" frameborder=0 scrolling=auto src="${contextPath}/servlet/MainServlet?task=frmright"> </iframe>
				</td>

				<td class="right_content" >
					<table>
						<tr>
							<td class="shopping_cart">
								<table>
									<tr>
										<td style="text-align: left;">
											<p id="p_totalCount" style="font-size: 20px;">
												<c:if test="${sessionScope.cartBean != null}">商品数量：${sessionScope.cartBean.totalCount} </c:if>
												<c:if test="${sessionScope.cartBean == null}">商品总数：0 ￥</c:if>
											</p>
											<p id="p_totalPrice" style="font-size: 20px;">
												<c:if test="${sessionScope.cartBean != null}">商品金额：${sessionScope.cartBean.totalPrice_format} </c:if>
												<c:if test="${sessionScope.cartBean == null}">商品金额：0.00 ￥</c:if>
											</p>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<c:if test="${empty regUserBean}">
									<form action="#" id="loginForm" method="post">
										<div class="title_box" style="background-size: 235px; height: 35px;font-size: small;text-align: center;line-height: 35px;">用户登录</div>
										<div class="border_box" style="width: 218px">
											<p style="font-weight: bold ;font-size: 15px">
												用户名：
												<input id="username" name="username" type="text" style="width: 110px" />
											</p>
											<p style="font-weight: bold;font-size: 15px">
												密&nbsp;&nbsp;&nbsp;&nbsp;码：
												<input id="password" name="password" type="password" style="width: 110px" />
											</p>
											<p>
												&nbsp;&nbsp;&nbsp;&nbsp;
												<input name="提交" type="button" value="登录" onclick="loginSystem();" />
												<input name="重置" type="reset" value="重置" />
											</p>
											<p align="center" >
												&nbsp;&nbsp;&nbsp;[
												<a href="${contextPath}/guest/register.jsp" target="frmright" style="font-size: 15px;text-decoration: none">新用户注册</a>
												] &nbsp;[
												<a href="${contextPath}/guest/findpass.jsp" target="frmright" style="font-size: 15px;text-decoration: none">忘记密码</a>
												]
											</p>

										</div>
									</form>
								</c:if>
								<c:if test="${!empty regUserBean}">
									<div class="title_box" style="background-size: 235px; height: 35px;font-size: small;text-align: center;line-height: 35px;">用户信息</div>
									<div class="border_box" style="width: 218px">
										<br />
										<p style="font-size: medium;font-weight:lighter;">欢迎登陆：${regUserBean.username}</p>
										<br />
										<p style="font-size: xx-small;font-weight: 500;">
											[
											<a href="${contextPath}/servlet/OrderServlet?task=getOrderList&uid=${regUserBean.username}&date=" + Math.random() + "" target="frmright">我的订单</a>
											]&nbsp;&nbsp; [
											<a href="${contextPath}/guest/updatePass.jsp" target="frmright">修改密码</a>
											]
										</p>
										<p style="font-size: xx-small;font-weight: 500;">
											[
											<a href="${contextPath}/guest/userInfo.jsp" target="frmright">个人信息</a>
											]&nbsp;&nbsp; [
											<a href="${contextPath}/servlet/LoginServlet?task=logoutRegUser" onclick="return confirm('确定要退出登录吗?')">退出系统</a>
											]
										</p>
									</div>
								</c:if>

							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>


		<div class="footer" align="center">
			<br />
			<p style="font-size: 10px">
				&copy;&nbsp;&nbsp;|&nbsp;软件学院2205王卓&nbsp;|&nbsp;版权所有&nbsp;|&nbsp;XM商城系统
			</p>
			<br />
		</div>

	</div>
</body>
</html>
