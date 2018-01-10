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
	//페이징 처리 안했을때 전체 결봐물 받아와서 뿌려주는컨트롤러
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
	//ModelAttribute로 page와 perPageNum 으로 파라미터가 넘어올경우  Criteria 타입의 객체로 리턴함 @RequestParam 으로 하면 코드가 길어지니까  그냥 모델로 파라미터로 받으면 쉽다.
	//상세보기 페이징 처리후 다시 리스트로 돌아갔을때 uri에 값을 파라미터로 받아와서 Criteria 에 set해놓은걸 다시 리턴해줘서 원래 창으로 돌아감 uri값은 uriComponents 객체를 이용해서 구해서 받아옴 
	@RequestMapping(value="/readPage",method=RequestMethod.GET)
	public void read(@RequestParam("bno") int bno, @ModelAttribute("cri") Criteria cri,Model model)  throws Exception{
		model.addAttribute(boardService.read(bno));
	}
	//삭제할때 read.jsp에서 사용한 bno를 파라미터로 가지고 와서 삭제 처리 계속 삭제 처리를 막기 위해서 RedirectAttributes 사용
	//데이터 유지 하기 전 (페이징 처리 하기전)
	@RequestMapping(value="/remove",method=RequestMethod.POST)
	public String remove(@RequestParam("bno") int bno,RedirectAttributes rttr) throws Exception{
		logger.info("delete result.............");
		
		boardService.remove(bno);
		rttr.addFlashAttribute("msg", "success");
		
		return "redirect:/board/listAll";
	}
	//게시물 삭제 했을때 uri 값으로 다시 원래 원래 페이지로 이동 하게 구성 (페이징 처리 했을때)
	@RequestMapping(value="/removePage",method=RequestMethod.POST)
	public String remove(@RequestParam("bno") int bno,Criteria cri,RedirectAttributes rttr) throws Exception{
		logger.info("delete result.............");
		
		boardService.remove(bno);
		// redirct하는 중간에  RedirectAttribute uri데이터(즉, page와 perPage의 데이터)를 가지고와서 addAttribute해준다 그럼 전에 있던 정보를 고스란히 redirect할때 보내주는 거지
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addFlashAttribute("msg", "success");
		
		return "redirect:/board/listPage";
	}
	//수정처리는 read에 있던 데이터를 그대로 가지고 오기 위해서 model에 담아서 가지고와서 view에 뿌려줌(페이징 처리 안했을때) 
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public void modifyGet(int bno,Model model) throws Exception{
		logger.info("modify show..................");
		model.addAttribute(boardService.read(bno));
	}
	//수정 처리 페이징 처리 했을때< 수정페이지로 넘어갈때 >(파라미터로 bno는 따로 받아오고 ,나머지 page와 perPageNum같은 경우는 Model형태로 파라미터를 받아와서 사용, 그래서 jsp페이지에서 파라미터를 보내야함 )
	@RequestMapping(value="/modifyPage",method=RequestMethod.GET)
	public void modifyPagingGet(@RequestParam("bno") int bno, @ModelAttribute("cri") Criteria cri, Model model) throws Exception{
		logger.info("modify show..................");
		model.addAttribute(boardService.read(bno));
	}
	//수정 처리는 post방식으로 사용자가 처리정보를 못보게 , 수정처리하고  redirect(페이징 처리 하기 전에 )
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public String modifyPOST(BoardVO vo,RedirectAttributes attr) throws Exception{
		logger.info("modify post....................."+vo.toString());
		boardService.modify(vo);
		attr.addFlashAttribute("msg", "success");
		return "redirect:/board/listAll";
	}
	//현제페이지에서 page와 perPageNum의 데이터를 가져오니가 @Param은 필요없고  redirect할때 addAttribute로  page,perPageNum 의 데이터를 보내서 그전 페이지를 유지 
	@RequestMapping(value="/modifyPage",method=RequestMethod.POST)
	public String modifyPagingPOST(Criteria cri,BoardVO vo,RedirectAttributes attr) throws Exception{
		logger.info("modify post......................"+vo.toString());
		
		boardService.modify(vo);
		
		attr.addAttribute("page", cri.getPage());
		attr.addAttribute("perPageNum", cri.getPerPageNum());
		attr.addFlashAttribute("msg", "success");
		return "redirect:/board/listPage";
	}
	//페이징 처리 연습(생성자를 아직 사용하지 않았기 때문에 실행하게 되면 default인 1,10에 해당하는 데이터들이 나옴
	@RequestMapping(value="/listCri", method=RequestMethod.GET)
	public void listAll(Criteria cri,Model model) throws Exception{
		logger.info("show list Paging Criteria................");
		
		model.addAttribute("list", boardService.listCriteria(cri));
	}
	//페이징 처리한 리스트 목록 보여주는 페이지 
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
