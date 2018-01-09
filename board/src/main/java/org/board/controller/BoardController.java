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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String registerPost(BoardVO vo,RedirectAttributes rttr) throws Exception {
		logger.info("register POST........................");
		logger.info(vo.toString());
		
		boardService.regist(vo);
		
		//model.addAttribute("result","success");
		rttr.addFlashAttribute("msg","success"); //redirct할때 같이 보냄  model과는 다르게 uri상에서는 보이지 않음  
		logger.info("rttr 메세지........................"+rttr.getFlashAttributes());
		//return "/board/success";
		return "redirect:/board/listAll";
	}
	
	@RequestMapping(value="/listAll",method=RequestMethod.GET)
	public void listAll(Model model) throws Exception{
		logger.info("listAll show...........................");
		
		model.addAttribute("list",boardService.listAll());
		
	}
	//RequestParam으로 bno를 받아와서 조회해서 모델에 담은 후 read쪽에 뿌려줌
	@RequestMapping(value="/read",method=RequestMethod.GET)
	public void read(@RequestParam("bno") int bno,Model model) throws Exception{
		logger.info("read show................");
		
		model.addAttribute(boardService.read(bno));
	}
	@RequestMapping(value="/remove",method=RequestMethod.POST)
	public String remove(@RequestParam("bno") int bno,RedirectAttributes rttr) throws Exception{
		logger.info("delete result.............");
		
		boardService.remove(bno);
		rttr.addFlashAttribute("msg", "success");
		
		return "redirect:/board/listAll";
	}
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public void modifyGet(int bno,Model model) throws Exception{
		logger.info("modify show..................");
		model.addAttribute(boardService.read(bno));
	}
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public String modifyPOST(BoardVO vo,RedirectAttributes attr) throws Exception{
		logger.info("modify post....................."+vo.toString());
		boardService.modify(vo);
		attr.addFlashAttribute("msg", "success");
		return "redirect:/board/listAll";
	}
}
