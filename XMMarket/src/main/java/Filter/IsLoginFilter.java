package Filter;

/**
 * 后端的过滤器
 */

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class IsLoginFilter implements Filter {
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		// System.out.println("IsLoginFilter执行...");
		/**
		 * 判断用户是否登录，如果未登录，转向到登录页面
		 * 登录时，将数据放到Session中
		 * HttpSession session = request.getSession();
		 * session.setAttribute("userBean", userBean);
		 */
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		// 判断哪些路径不需要过滤。
		String requestURI = request.getRequestURI();
		// System.out.println("requestURI = " + requestURI);
		if (requestURI.indexOf("/servlet/LoginServlet") != -1) {
			chain.doFilter(request, response);
		} else {
			if (session.getAttribute("userBean") != null) {
				// 说明用户已经登录,可以访问下一个组件。
				chain.doFilter(request, response);
			} else {
				// 说明用户还没有登录，重定向登录页面。
				String loginURL = request.getContextPath() + "/admin/login.jsp";
				// response.sendRedirect(loginURL);

				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println("<script language='javascript'>");
				out.println("window.alert('您还未登录系统，或者系统会话已超时，请重新登录');");
				out.println("window.top.location.href = '" + loginURL + "';");
				out.println("</script>");
			}
		}
	}
}