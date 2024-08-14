package servlet;

import Entity.UserBean;
import Service.UserBeanService;
import Service.impl.UserBeanServiceImpl;
import Utils.JsonUtil;
import Utils.ResultData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

@WebServlet(name = "LoginServlet", urlPatterns = "/servlet/LoginServlet")
public class LoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String task = request.getParameter("task");
		 if (task.equals("loginRegUser")) {
			this.loginRegUser(request, response);
		} else if (task.equals("logoutRegUser")) {
			this.logoutRegUser(request, response);
		}
	}

	private void logoutRegUser(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		if (session.getAttribute("regUserBean") != null) {
			session.removeAttribute("regUserBean");
		}
		String loginURL = request.getContextPath() + "/servlet/MainServlet?task=guestMainPage";
		try {
			response.sendRedirect(loginURL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void loginRegUser(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		ResultData responseData = new ResultData();
		UserBean user = new UserBean();
		user.setUsername(username);
		user.setPassword(password);
		System.out.println("username=" + username);
		UserBeanService userBeanService = new UserBeanServiceImpl();
		if (userBeanService.login(user) != null) {
			HttpSession session = request.getSession();
			// 1.将用户存入session
			user = userBeanService.login(user);
			// System.out.println("登录用户："+user);
			session.setMaxInactiveInterval(-1);
			session.setAttribute("regUserBean", user);
			// 2.设置返回消息
			responseData.setFlag(true);
			responseData.setMessage("登录成功");
		} else {
			responseData.setFlag(false);
			responseData.setMessage("用户名密码不匹配，请重新输入");
			// System.out.println("用户名密码不匹配，请重新输入");
		}

		response.setContentType("text/html");
		try {
			PrintWriter out = response.getWriter();
			// 讲对象转换为json格式的字符串
			String jsonStr = JsonUtil.parseString(responseData);
			out.print(jsonStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
