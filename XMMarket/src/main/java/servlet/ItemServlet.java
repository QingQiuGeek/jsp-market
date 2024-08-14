package servlet;

import Entity.CategoryBean;
import Entity.ItemBean;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import Service.CategoryBeanService;
import Service.ItemBeanService;
import Service.impl.CategoryBeanServiceImpl;
import Service.impl.ItemBeanServiceImpl;
import Utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

@WebServlet(name = "ItemServlet", urlPatterns = "/servlet/ItemServlet")
public class ItemServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String task = request.getParameter("task");
		 if (task.equals("showDetail")) {
			this.showDetail(request, response);
		}
	}


	private void showDetail(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = null;
		ResultData responseData = new ResultData();
		ItemBean itemBean = null;
		HttpSession session=request.getSession();
		String itemid = request.getParameter("itemid");
		ItemBeanService itemBeanService = new ItemBeanServiceImpl();
		itemBean = itemBeanService.getItemById(itemid);
		System.out.println("itemBean=="+itemBean);
		try {
			out = response.getWriter();
			if (itemBean != null) {
				responseData.setFlag(true);
				session.setAttribute("itemBeanDetail", itemBean);
//				request.getRequestDispatcher("/guest/showdetails.jsp").forward(request, response);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseData.setFlag(false);
			responseData.setMessage("商品详情获取失败");
		}

		String jsonStr = JsonUtil.parseString(responseData);
		out.print(jsonStr);
		out.flush();
		out.close();
	}
}
