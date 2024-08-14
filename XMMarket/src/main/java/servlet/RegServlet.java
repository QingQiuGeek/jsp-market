package servlet;

import Entity.UserBean;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "RegServlet", urlPatterns = "/servlet/RegServlet")
public class RegServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String task = request.getParameter("task");
		if (task.equals("modifySelf")) {
			this.modifySelf(request, response);
		} else if (task.equals("check")) {
			this.check(request, response);
		} else if (task.equals("register")) {
			this.register(request, response);
		} else if (task.equals("updatePass")) {
			this.updatePass(request, response);
		} else if (task.equals("findPass")) {
			this.findPass(request, response);
		}
	}


	private void findPass(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		ResultData responseData = new ResultData();
		PrintWriter out = null;
		try {
			UserBeanService userBeanService = new UserBeanServiceImpl();
			out = response.getWriter();
			// 1.获取表单中提交的数据
			String username = request.getParameter("username");
			String realname = request.getParameter("realname");
			String phone = request.getParameter("phone");
//			String email = request.getParameter("email");
			System.out.println("调用changePwd........");
			// 2.调用service查找密码
			UserBean userBean = userBeanService.findByUserName(username);
			if (userBean.getUsername().equals(username) && userBean.getRealname().equals(realname)
					 && userBean.getPhone().equals(phone)) {
				responseData.setMessage("密码找回成功，请牢记您的密码：" + userBean.getPassword());
			}
			responseData.setFlag(true);

		} catch (IOException e) {
			e.printStackTrace();
			responseData.setFlag(false);
			responseData.setMessage("找回失败，错误信息：" + e.getMessage());
		}
		// 讲对象转换为json格式的字符串
		String jsonStr = JsonUtil.parseString(responseData);
		out.print(jsonStr);
		out.flush();
		out.close();
	}


	private void updatePass(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		ResultData responseData = new ResultData();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			// 1.获取表单中提交的数据
			String newpwd = request.getParameter("newpassword");
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


	private void register(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = null;
		ResultData responseData = new ResultData();
		Map<String, Object> dataMap = new LinkedHashMap<>();
		try {
			out = response.getWriter();
			// 1.获取表单中提交的数据
			FileItemFactory itemFactory = new DiskFileItemFactory();
			ServletFileUpload fileUpload = new ServletFileUpload(itemFactory);
			List<FileItem> itemList = fileUpload.parseRequest(request);
			for (FileItem fileItem : itemList) {
				if (fileItem.isFormField()) {
					// 说明是表单组件
					String filed_name = fileItem.getFieldName();
					String filed_value = fileItem.getString("UTF-8");
					dataMap.put(filed_name, filed_value);
				}
			}


			// 2.将数据封装到bean中
			UserBean userBean = new UserBean();
			BeanUtils.copyProperties(userBean, dataMap);
			// System.out.println(userBean);
			// 3.调用service添加系统用户
			UserBeanService userBeanService = new UserBeanServiceImpl();
			userBeanService.insertRegUser(userBean);
			responseData.setFlag(true);
			responseData.setMessage("注册成功");
		} catch (Exception e) {
			e.printStackTrace();
			responseData.setFlag(false);
			responseData.setMessage("注册失败，错误信息：" + e.getMessage());
		}
		String jsonStr = JsonUtil.parseString(responseData);
		out.print(jsonStr);
		out.flush();
		out.close();
	}


	private void check(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = null;
		ResultData responseData = new ResultData();
		String username = request.getParameter("username");
		UserBeanService userBeanService = new UserBeanServiceImpl();
		try {
			out = response.getWriter();
			UserBean userBean = userBeanService.findByUserName(username);
			if (userBean != null) {
				responseData.setFlag(false);
				responseData.setMessage("用户名已存在");
			} else {
				responseData.setFlag(true);
			}
		} catch (IOException e) {
			e.printStackTrace();
			responseData.setFlag(false);
			responseData.setMessage("注册失败，错误信息：" + e.getMessage());
		}
		String jsonStr = JsonUtil.parseString(responseData);
		out.print(jsonStr);
		out.flush();
		out.close();
	}

	private void modifySelf(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = null;
		Integer uid = Integer.parseInt(request.getParameter("uid"));
		ResultData responseData = new ResultData();
		Map<String, Object> dataMap = new LinkedHashMap<>();
		HttpSession session = request.getSession();
		try {
			out = response.getWriter();
			// 1.获取表单中提交的数据
			FileItemFactory itemFactory = new DiskFileItemFactory();
			ServletFileUpload fileUpload = new ServletFileUpload(itemFactory);
			List<FileItem> itemList = fileUpload.parseRequest(request);
			for (FileItem fileItem : itemList) {
				if (fileItem.isFormField()) {
					// 说明是表单组件
					String filed_name = fileItem.getFieldName();
					String filed_value = fileItem.getString("UTF-8");
					dataMap.put(filed_name, filed_value);
				}
			}

			// 2.将数据封装到bean中
			UserBean userBean = new UserBean();
			BeanUtils.copyProperties(userBean, dataMap);
			userBean.setUid(uid);
			// System.out.println("userBean===" + userBean);
			// 3.调用service添加系统用户
			UserBeanService userBeanService = new UserBeanServiceImpl();
			userBeanService.updateRegUser(userBean);

			if (session.getAttribute("regUserBean") != null) {
				userBean = userBeanService.getUserById(uid);
				session.removeAttribute("regUserBean");
				session.setAttribute("regUserBean", userBean);
				System.out.println("更换session中的用户：" + userBean);
			}

			responseData.setFlag(true);
			responseData.setMessage("用户数据修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			responseData.setFlag(false);
			responseData.setMessage("修改失败，错误信息：" + e.getMessage());
		}
		String jsonStr = JsonUtil.parseString(responseData);
		out.print(jsonStr);
		out.flush();
		out.close();
	}
}
