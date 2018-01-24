package com.board.interceptor;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.board.domain.UserVO;
import org.board.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

public class AuthInterceptor extends HandlerInterceptorAdapter{
	
	@Inject
	UserService userService;

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
			
			// loginCookie 의 쿠키정보를 받아와서 객체를 생성
			Cookie loginCookie=WebUtils.getCookie(request, "loginCookie");
			
			if(loginCookie !=null) {
				// 쿠키정보를 업데이트해서 vo객체에 담고
				UserVO userVO= userService.checkLoginBefore(loginCookie.getValue());
				
				logger.info("USERVO: "+userVO);
				
				if(userVO !=null) {
					//새로운 세션정보를 set해줌 
					session.setAttribute("login", userVO);
					return true;
				}
			}
			
			response.sendRedirect("/user/login");
			
			return false;
		}
		return true;
	}
}
