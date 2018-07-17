package org.board.controller;

import javax.servlet.http.HttpSession;

import org.board.domain.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebSocketController {

	@RequestMapping(value="/chatting",method=RequestMethod.GET)
	public ModelAndView chat(HttpSession session) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		UserVO user = (UserVO)session.getAttribute("login");
		System.out.println("유저 정보:" + user);
		mav.addObject("userid",user.getUid()); 
		mav.setViewName("user/chatting");
		return mav;
	}
}
