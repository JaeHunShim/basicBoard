package org.board.controller;

import java.util.List;

import javax.inject.Inject;

import org.board.domain.PageMaker;
import org.board.domain.SeachCriteria;
import org.board.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sboard/*")
public class SearchController { //기존의 boardController와 동일 함 
	
	private static final Logger logger= LoggerFactory.getLogger(SearchController.class);
	
	@Inject
	BoardService boardService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public void listPage(@ModelAttribute("cri") SeachCriteria cri,Model model) throws Exception{	
		logger.info(cri.toString());
		
		model.addAttribute("list",boardService.listCriteria(cri));
		PageMaker pageMaker=new PageMaker();
		pageMaker.setCri(cri);
		//pageMaker.setTotalCount(131); //연습으로 총 개시물을 131개로 잡아주고 잘 돌아가는지 해봄
		pageMaker.setTotalCount(boardService.listCountCriteria(cri));
		
		model.addAttribute("pageMaker",pageMaker);
	}
	// Ajax로 업로드파일 정보 불러오기  url에서 (pathvariable 로 bno를 받아옴)
	
	@RequestMapping("/getAttach/{bno}")
	@ResponseBody
	public List<String> getAttach(@PathVariable("bno")Integer bno) throws Exception {
		
		return boardService.getAttech(bno);
	}
}
