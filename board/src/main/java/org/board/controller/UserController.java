package org.board.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.board.domain.UserVO;
import org.board.dto.LoginDTO;
import org.board.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	}
}
