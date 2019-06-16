package org.fkjava.travel.core.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.fkjava.travel.core.domain.User;

public class CheckLoginFilter extends HttpFilter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// 获取Session对象，然后判断Session里面是否有user对象
		HttpSession session = request.getSession();
		// 如果没有user对象，表示未登录，重定向到登录页面、原有操作不能继续执行
		User user = (User) session.getAttribute("user");
		if (user == null) {

			// 标记是否需要登录，如果需要登录，并且此时还未登录，那么就要重定向
			boolean needLogin = true;
			// 获取初始化参数，在web.xml文件中配置
			String exclude = super.getInitParameter("exclude");
			// 逗号隔开多个路径
			String[] excludePaths = exclude.split(",");

			// 获取请求的URL，并且把应用的Context Path截取掉不要！
			String url = request.getRequestURI();
			url = url.substring(request.getContextPath().length());
			// System.out.println("请求的URL: " + url);

			// 检查访问的URL，是否以path开头，如果是则表示【排除、不需要登录】
			for (String path : excludePaths) {
				if (url.startsWith(path)) {
					// 排除掉的，不需要登录的
					needLogin = false;
					break;
				}
			}

			// 重定向
			if (needLogin) {
				response.sendRedirect(request.getContextPath() + "/security/login");
				return;
			}
		}

		// 如果已经登录，则继续执行原有的操作
		super.doFilter(request, response, chain);
	}
}
