package servlet;

import Entity.*;
import Service.CartService;
import Service.OrderBeanService;
import Service.impl.CartServiceImpl;
import Service.impl.OrderBeanServiceImpl;
import Utils.JsonUtil;
import Utils.PageUtil;
import Utils.ResultData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

@WebServlet(name = "OrderServlet", urlPatterns = "/servlet/OrderServlet")
public class OrderServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String task = request.getParameter("task");
		if (task.equals("newOrder")) {
			this.newOrder(request, response);
		} else if (task.equals("getOrderList")) {
			this.getOrderList(request, response);
		} else if (task.equals("payOrder")) {
			this.payOrder(request, response);
		} else if (task.equals("modify")) {
			this.modify(request, response);
		}
	}

	private void modify(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = null;
		ResultData responseData = new ResultData();
		OrderBeanService orderBeanService = new OrderBeanServiceImpl();
		String orderid = request.getParameter("orderid");
		String getaddress = request.getParameter("getaddress");
//		String getpostcode = request.getParameter("getpostcode");
		String getphone = request.getParameter("getphone");
//		String getemail = request.getParameter("getemail");
		System.out.println(orderid + "\t" + getaddress + "\t" +  "\t" + getphone + "\t");
		OrderBean orderBean = orderBeanService.getOrderBeanByOrderId(orderid);
		orderBean.setGetaddress(getaddress);
//		orderBean.setGetemail(getemail);
		orderBean.setGetphone(getphone);
//		orderBean.setGetpostcode(getpostcode);
		try {
			orderBeanService.updateOrderBean(orderBean);
			out = response.getWriter();
			responseData.setFlag(true);
			responseData.setMessage("订单信息修改成功，点击确定刷新页面");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			responseData.setFlag(false);
			responseData.setMessage("修改失败，错误信息：" + e.getMessage());
		}
		String jsonStr = JsonUtil.parseString(responseData);
		out.print(jsonStr);
		out.flush();
		out.close();
	}

	private void payOrder(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		String orderid = request.getParameter("orderid");
		ResultData responseData = new ResultData();
		PrintWriter out = null;
		OrderBean orderBean = null;
		HttpSession session = request.getSession();
		OrderBeanService orderBeanService = new OrderBeanServiceImpl();
		try {
			out = response.getWriter();
			orderBean = orderBeanService.getOrderBeanByOrderId(orderid);
			System.out.println(orderBean);
			if (orderBean.getPaytype().equals("否")) {
				orderBean.setPaytype("是");
				orderBeanService.updateOrderBean(orderBean);
				responseData.setFlag(true);
				responseData.setMessage("付款成功");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			responseData.setFlag(false);
			responseData.setMessage("付款失败，错误信息：" + e.getMessage());
		}

		String jsonStr = JsonUtil.parseString(responseData);
		out.print(jsonStr);
		out.flush();
		out.close();

	}


	private void getOrderList(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		String uid = request.getParameter("uid");
		OrderBeanService orderBeanService = new OrderBeanServiceImpl();
		// 实例化分页对象
		PageUtil pageUtil = new PageUtil(request);
		pageUtil.setPageSize(8);
		// 获取分页的四个变量
		// 1.总记录数
		pageUtil.setRsCount(orderBeanService.getOrderCount(uid));
		// 2.当前页码
		int currentPage = pageUtil.getCurrentPage();
		// 3.总页数
		int pageCount = pageUtil.getPageCount();
		// 4.每页显示多少条
		int pageSize = pageUtil.getPageSize();
		// 产生工具栏
		String pageTool = pageUtil.createPageTool(PageUtil.BbsText);
		// 设置每页显示的起始位置
		int startIndex = (currentPage - 1) * pageSize;
		List<OrderBean> orderBeans = orderBeanService.getOrderList(startIndex, pageSize, uid);
		request.setAttribute("pageTool", pageTool);
		request.setAttribute("orderList", orderBeans);
		try {
			request.getRequestDispatcher("/guest/showOrderForm.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void newOrder(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = null;
		ResultData responseData = new ResultData();
		HttpSession session = request.getSession();
		CartBean cartBean = null;
		UserBean regUserBean = null;
		String msg = request.getParameter("msg");
		Set<Integer> set = new HashSet<>();
		try {
			out = response.getWriter();
			// 调用业务方法
			if (session.getAttribute("regUserBean") == null) {
				responseData.setFlag(false);
				responseData.setMessage("您还未登录系统，请先登录再提交订单");
			} else if (session.getAttribute("cartBean") == null) {
				throw new RuntimeException("会话中的购物车为空，提交订单失败");
			} else {
				regUserBean = (UserBean) session.getAttribute("regUserBean");
				cartBean = (CartBean) session.getAttribute("cartBean");
				List<ItemBean> itemList = cartBean.getItemList();
				// 删除购物车中的商品
				Iterator<ItemBean> iterator = itemList.iterator();
				while (iterator.hasNext()) {
					ItemBean rowBean = iterator.next();
					// System.out.println("rowBean==" + rowBean);
					int cid = rowBean.getMinid();
					set.add(cid);
				}
				int mctypesize = set.size();
				// System.out.println("mctypesize=" + mctypesize);
				OrderBeanService orderBeanService = new OrderBeanServiceImpl();
				orderBeanService.insert(regUserBean, cartBean, mctypesize, msg, itemList);
				cartBean.setTotalCount(0);
				cartBean.getItemList().clear();
				cartBean.setTotalPrice(new BigDecimal(0.00));
				// 计算购物车中的金额信息
				CartService cartService = new CartServiceImpl();
				cartBean = cartService.updateCart(cartBean);
				// 重新将cartBean放入到session中
				session.setAttribute("cartBean", cartBean);
				responseData.setFlag(true);
				responseData.setData(cartBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseData.setFlag(false);
			responseData.setMessage("提交失败，错误信息：" + e.getMessage());
		}
		String jsonStr = JsonUtil.parseString(responseData);
		// System.out.println(jsonStr);
		out.print(jsonStr);
		out.flush();
		out.close();
	}

}
