package com.bms.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bms.util.Constant;
import com.bms.util.ErrorCode;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception exception)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj, ModelAndView mav) throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj) throws Exception {
		String requestPath = request.getServletPath();
		HttpSession session = request.getSession();
		
		for (String passUrl : Constant.PASS_URL) {
			if (requestPath.equals(passUrl)) {
				return true;
			}
		}
		
		if (session.getAttribute("user") == null) {
			request.getRequestDispatcher(ErrorCode.PAGE_NO_LOGIN).forward(request, response);;
			return false;
		}
		return true;
	}

}
