package servlet;

import Entity.CategoryBean;
import Service.CategoryBeanService;
import Service.impl.CategoryBeanServiceImpl;
import Utils.JsonUtil;
import Utils.ResultData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "CategoryServlet", urlPatterns = "/servlet/CategoryServlet")
public class CategoryServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String task = request.getParameter("task");
		if (task.equals("saveData")) {
			this.saveData(request, response);
		} else if (task.equals("deleteCategory")) {
			this.deleteCategory(request, response);
		} else if (task.equals("updateCategory")) {
			this.updateCategory(request, response);
		}
	}


	private void updateCategory(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		ResultData responseData = new ResultData();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			// 1.获取表单中提交的数据
			String parentid = request.getParameter("parentid");
			String cid = request.getParameter("cid");
			String classname = request.getParameter("classname");
			if (classname == null || classname.equals("")) {
				responseData.setFlag(false);
				responseData.setMessage("类别名不能为空");
			} else {
				// 2.将数据封装到bean中
				CategoryBean categoryBean = new CategoryBean(Integer.parseInt(cid), classname,
						Integer.parseInt(parentid));
				// 3.调用service保存类别
				CategoryBeanService categoryBeanService = new CategoryBeanServiceImpl();
				categoryBeanService.updateCategory(categoryBean);
				responseData.setFlag(true);
				responseData.setMessage("数据添加成功，是否继续添加？");
			}

			// 讲对象转换为json格式的字符串
			String jsonStr = JsonUtil.parseString(responseData);
			out.print(jsonStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void deleteCategory(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		CategoryBeanService categoryBeanService = new CategoryBeanServiceImpl();
		PrintWriter out = null;
		ResultData responseData = new ResultData();
		// 1.获取客户端传过来的参数
		String parentid = request.getParameter("parentid");
		String cid = request.getParameter("cid");
		try {
			out = response.getWriter();
			categoryBeanService.deleteCategory(cid, parentid);
			responseData.setFlag(true);
		} catch (Exception e) {
			// 处理删除时发生的异常
			e.printStackTrace();
			responseData.setFlag(false);
			responseData.setMessage("删除失败，错误信息：" + e.getMessage());
		}

		String jsonStr = JsonUtil.parseString(responseData);
		out.print(jsonStr);
		out.flush();
		out.close();
	}


	private void saveData(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		try {
			ResultData responseData = new ResultData();
			// 1.获取表单中提交的数据
			String parentid = request.getParameter("parentid");
			String cid = request.getParameter("cid");
			String classname = request.getParameter("classname");
			if (classname == null || classname.equals("")) {
				responseData.setFlag(false);
				responseData.setMessage("类别名不能为空");
				System.out.println("类别名不能为空");
			} else {
				// 2.将数据封装到bean中
				CategoryBean categoryBean = new CategoryBean(Integer.parseInt(cid), classname,
						Integer.parseInt(parentid));
				// 3.调用service保存类别
				CategoryBeanService categoryBeanService = new CategoryBeanServiceImpl();
				categoryBeanService.insertCategory(categoryBean);
				responseData.setFlag(true);
				responseData.setMessage("数据添加成功，是否继续添加？");
			}
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
