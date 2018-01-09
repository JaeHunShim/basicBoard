package org.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice //컨트롤러에서 발생하는 Exception을 처리하는 클래스라는 걸 명시
public class CommonExceptionAdvice {

	private static final Logger logger= LoggerFactory.getLogger("CommonExceptionAdvice.class");
	
	@ExceptionHandler(Exception.class) // 적절한 타입의 Exception을 처리
	private ModelAndView errorModelAndView(Exception ex) {
		
		ModelAndView modelAndView= new ModelAndView();
		
		modelAndView.setViewName("/exception/error_common");
		modelAndView.addObject("exception", ex);
		
		return modelAndView;
	}
}
