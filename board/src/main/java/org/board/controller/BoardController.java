package org.board.controller;

import javax.inject.Inject;

import org.board.domain.BoardVO;
import org.board.domain.Criteria;
import org.board.domain.PageMaker;
import org.board.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	//글작성 페이지 불러오기 
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public void registerGet(BoardVO vo,Model model) throws Exception {
		logger.info("register GET........................");
	}
	//글작성 처리  RedirectAttributes 를 사용해서  리다이렉트 처리할때 새로고침 했을때 게속 삽입할수 없게 처리 
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
	//삭제할때 read.jsp에서 사용한 bno를 파라미터로 가지고 와서 삭제 처리 계속 삭제 처리를 막기 위해서 RedirectAttributes 사용
	@RequestMapping(value="/remove",method=RequestMethod.POST)
	public String remove(@RequestParam("bno") int bno,RedirectAttributes rttr) throws Exception{
		logger.info("delete result.............");
		
		boardService.remove(bno);
		rttr.addFlashAttribute("msg", "success");
		
		return "redirect:/board/listAll";
	}
	//수정처리는 read에 있던 데이터를 그대로 가지고 오기 위해서 model에 담아서 가지고와서 view에 뿌려줌 
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public void modifyGet(int bno,Model model) throws Exception{
		logger.info("modify show..................");
		model.addAttribute(boardService.read(bno));
	}
	//수정 처리는 post방식으로 사용자가 처리정보를 못보게 , 수정처리하고  redirect 
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public String modifyPOST(BoardVO vo,RedirectAttributes attr) throws Exception{
		logger.info("modify post....................."+vo.toString());
		boardService.modify(vo);
		attr.addFlashAttribute("msg", "success");
		return "redirect:/board/listAll";
	}
	//페이징 처리 연습(생성자를 아직 사용하지 않았기 때문에 실행하게 되면 default인 1,10에 해당하는 데이터들이 나옴
	@RequestMapping(value="/listCri", method=RequestMethod.GET)
	public void listAll(Criteria cri,Model model) throws Exception{
		logger.info("show list Paging Criteria................");
		
		model.addAttribute("list", boardService.listCriteria(cri));
	}
	@RequestMapping(value="/listPage",method=RequestMethod.GET)
	public void listPage(@ModelAttribute("cri") Criteria cri,Model model) throws Exception{
		
		logger.info(cri.toString());
		
		model.addAttribute("list",boardService.listCriteria(cri));
		PageMaker pageMaker=new PageMaker();
		pageMaker.setCri(cri);
		//pageMaker.setTotalCount(131); //연습으로 총 개시물을 131개로 잡아주고 잘 돌아가는지 해봄
		pageMaker.setTotalCount(boardService.listCountCriteria(cri));
		
		model.addAttribute("pageMaker",pageMaker);
		
	}
}
