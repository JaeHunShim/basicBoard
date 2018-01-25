package org.board.controller;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.board.domain.UserVO;
import org.board.dto.LoginDTO;
import org.board.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Inject
	UserService userService;
	// 로그인 화면 불러오기
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public void loginGET(@ModelAttribute("dto") LoginDTO dto) {
		
	}
	//로그인 처리 
	@RequestMapping(value="/loginPost",method=RequestMethod.POST)
	public void loginPOST(LoginDTO dto, HttpSession session, Model model) throws Exception{
		
		UserVO vo = userService.login(dto);
		
		if(vo==null) {
			
			return;
		}
		model.addAttribute("userVO", vo);
		
		if(dto.isUseCookie()) {
			
			int amount = 60*60*24*7;
			
			Date sessionlimit= new Date(System.currentTimeMillis()+(1000*amount));
			
			userService.keepLogin(vo.getUid(), session.getId(), sessionlimit);
		}
	}
	//로그아웃 처리
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
		
		Object obj=session.getAttribute("login");
		
		if(obj !=null) {
			UserVO vo = (UserVO)obj;
			
			session.removeAttribute("login");
			session.invalidate();
			
			Cookie loginCookie=WebUtils.getCookie(request, "loginCookie");
			
			if(loginCookie != null) {
				loginCookie.setPath("/"); //모든경로에 접근가능하도록 하기 위해서 / setpath지정 
				loginCookie.setMaxAge(0);
				response.addCookie(loginCookie);
				userService.keepLogin(vo.getUid(), session.getId(), new Date());
			}
		}
		return "redirect:/user/login";
	}
	
	
}
