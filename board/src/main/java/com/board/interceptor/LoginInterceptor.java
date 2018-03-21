package com.board.interceptor;

import javax.servlet.http.Cookie;
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
			if(request.getParameter("useCookie") != null) {
				
				logger.info("remember id---------------");
				Cookie loginCookie=new Cookie("loginCookie",session.getId()); //session 에 있는 아이디를 가져와서 쿠키객체를 생성한다(loginCookie라는 키로)
				loginCookie.setPath("/");// 모든경로에서 쿠키정보를 접근 가능하도록 함 그래서 페이지가 바뀌어도 쿠키 정보를 볼수 잇음 
				loginCookie.setMaxAge(60*60*24*7); // 60초*60분*24시간*7일 = 7일간 쿠키유지
			
				response.addCookie(loginCookie); //생성한 쿠키를  response에 담아서 보낸다
			}
			//세션의 정보를 가지고와서 dest 에 담고 
			//response.sendRedirect("/");
			Object dest=session.getAttribute("dest");
			//세션에 정보가 있다면 해당 url+query 로 이동하고 세션에 정보가 없으면 루트경로로 이동 
			response.sendRedirect(dest !=null?(String)dest:"/");
		}
	}
	//세션을 날리는 부분 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception{
		
		HttpSession session=request.getSession();
		
		if(session.getAttribute(LOGIN) != null) {
			
			logger.info("clear login data before");
			session.removeAttribute(LOGIN); // session에 저장되어있는 데이터가 있으면 삭제 하기 
		}
		
		return true;
	}
}
