package servlet;


import Service.UserBeanService;
import Service.impl.UserBeanServiceImpl;
import Utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(name = "UserServlet", urlPatterns = "/servlet/UserServlet")
public class UserServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String task = request.getParameter("task");
		if (task.equals("changePwd")) {
			this.changePwd(request, response);
		}
	}

	private void changePwd(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		ResultData responseData = new ResultData();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			// 1.获取表单中提交的数据
			String newpwd = request.getParameter("newpwd");
			String uid = request.getParameter("uid");
			System.out.println("调用changePwd........");
			// 2.调用service修改密码
			UserBeanService userBeanService = new UserBeanServiceImpl();
			userBeanService.changePwd(uid, newpwd);
			responseData.setFlag(true);
			responseData.setMessage("密码修改成功，请重新登录");

		} catch (IOException e) {
			e.printStackTrace();
			responseData.setFlag(false);
			responseData.setMessage("修改失败，错误信息：" + e.getMessage());
		}
		// 讲对象转换为json格式的字符串
		String jsonStr = JsonUtil.parseString(responseData);
		out.print(jsonStr);
		out.flush();
		out.close();
	}
}
