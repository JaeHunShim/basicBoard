package com.board.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	private static final String LOGIN="login";
	
	private static final Logger logger= LoggerFactory.getLogger(LoginInterceptor.class);
	
	// controller에서 담겨져 있는 vo객체를 session에 담는다 
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
							Object handler, ModelAndView mav) throws Exception{
		
		HttpSession session=request.getSession(); // session 객체를 생성 
		
		ModelMap modelMap=mav.getModelMap(); // ModelMap 객체 생성 
		Object userVO=modelMap.get("userVO");// vo데이터를 가지고 와서 map에 담음 
		
		if(userVO != null) {
			
			logger.info("new login success");
			session.setAttribute(LOGIN, userVO); //session에 vo객체를 다 넣어놓음 
			response.sendRedirect("/");
		}
	}
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception{
		
		HttpSession session=request.getSession();
		
		if(session.getAttribute(LOGIN) != null) {
			
			logger.info("login data before");
			session.removeAttribute(LOGIN); // session에 저장되어있는 데이터가 있으면 삭제 하기 
		}
		
		return true;
	}
}
