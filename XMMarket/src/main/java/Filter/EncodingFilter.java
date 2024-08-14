package Filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EncodingFilter implements Filter {
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");

		//将当前工程的路径放到request作用域中。
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String contextPath = request.getContextPath();
		request.setAttribute("contextPath", contextPath);
		chain.doFilter(request, response);
	}
}
