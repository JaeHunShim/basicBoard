package org.board.controller;

import javax.inject.Inject;

import org.board.domain.BoardVO;
import org.board.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	private BoardService boardService;
	
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public void registerGet(BoardVO vo,Model model) throws Exception {
		logger.info("register GET........................");
	}
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String registerPost(BoardVO vo,Model model) throws Exception {
		logger.info("register POST........................");
		logger.info(vo.toString());
		
		boardService.regist(vo);
		
		model.addAttribute("result","success");
		/*return "/board/success";*/
		return "redirect:/board/listAll";
	}
	//rediect처리한 페이지 이동
	@RequestMapping(value="/listAll",method=RequestMethod.GET)
	public void listAll(Model model) throws Exception{
		logger.info("listAll show...........................");
		
	}
}
