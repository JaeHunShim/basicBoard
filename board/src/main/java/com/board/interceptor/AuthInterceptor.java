package com.board.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter{

	private static final Logger logger= LoggerFactory.getLogger(AuthInterceptor.class);
	// 이부분은 원래 가려고했던 url정보를 session에 저장해놓기 위해서 사용함 예를들어서 로그인하지 않은 사용자가 글쓰기를 갔을때 login 화면으로 가고 login을 하면 글쓰기 페이지로 이동 
	private void saveDest(HttpServletRequest req) {
		
		String uri=req.getRequestURI();
		// url 에 경로뒤에서 포함된 쿼리를 문자열로 반환하는 역할 
		String query=req.getQueryString();
		
		if(query==null || query.equals("null")) {
			query="";
		}else {
			query="?"+query;
		}
		//요청방식이 get방식이라면 uri와 뒤의 query정보를 세션에 저장한다.(dest라는 키값으로 )
		if(req.getMethod().equals("GET")) {
			logger.info("dest:" + uri+query);
			
			req.getSession().setAttribute("dest", uri+query);
		}
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception{
		
		HttpSession session=request.getSession();
		// login 정보 즉, 세션의 정보가 없다면 login 정보로 가게끔 
		if(session.getAttribute("login") == null) {
			
			logger.info("current user is not login");
			
			saveDest(request);
			
			response.sendRedirect("/user/login");
			
			return false;
		}
		return true;
	}
}
