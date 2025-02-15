package servlet;

import Entity.CategoryBean;
import Entity.ItemBean;
import Service.CategoryBeanService;
import Service.ItemBeanService;
import Service.impl.CategoryBeanServiceImpl;
import Service.impl.ItemBeanServiceImpl;
import Utils.PageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "MainServlet", urlPatterns = "/servlet/MainServlet")
public class MainServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String task = request.getParameter("task");

		if (task.equals("guestMainPage")) {
			this.guestMainPage(request, response);
		} else if (task.equals("frmright")) {
			this.frmright(request, response);
		}
	}

	private void frmright(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		ItemBeanService itemBeanService = new ItemBeanServiceImpl();
		Map<String, Object> searchMap = new HashMap<String, Object>();
		// 获取查询参数
		String maxid = request.getParameter("maxid");
		String minid = request.getParameter("minid");
		String searchKeyWord = request.getParameter("searchKeyWord");
		searchMap.put("maxid", maxid);
		searchMap.put("minid", minid);
		searchMap.put("searchKeyWord", searchKeyWord);
		try {
			// 实例化分页对象
			PageUtil pageUtil = new PageUtil(request);
			pageUtil.setPageSize(12);
			// 获取分页的四个变量
			// 1.总记录数
			pageUtil.setRsCount(itemBeanService.getItemCount(searchMap));
			// 2.当前页码
			int currentPage = pageUtil.getCurrentPage();
			// 3.总页数
			int pageCount = pageUtil.getPageCount();
			// 4.每页显示多少条
			int pageSize = pageUtil.getPageSize();

			// 产生工具栏
			String pageTool = pageUtil.createPageTool(PageUtil.BbsImage);
			// 设置每页显示的起始位置
			int startIndex = (currentPage - 1) * pageSize;
			List<ItemBean> itemList = itemBeanService.getItemList(startIndex, pageSize, searchMap);
			// 将查询条件存放到作用域中
			request.setAttribute("pageTool", pageTool);
			request.setAttribute("itemList", itemList);
			request.setCharacterEncoding("utf-8");
			request.getRequestDispatcher("/guest/product.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void guestMainPage(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		try {
			CategoryBeanService categoryBeanService = new CategoryBeanServiceImpl();
			List<CategoryBean> categoryBeanList = categoryBeanService.getCategoryList();
			request.setAttribute("categoryBeanList", categoryBeanList);
			request.getRequestDispatcher("/guest/main.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
