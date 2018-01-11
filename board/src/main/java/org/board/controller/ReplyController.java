package org.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.board.domain.Criteria;
import org.board.domain.PageMaker;
import org.board.domain.ReplyVO;
import org.board.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/replies")
public class ReplyController {
	
	@Inject
	ReplyService replyService;
	
	@RequestMapping(value="", method=RequestMethod.POST)
	//댓글 등록하는 부분 (@RequestBody로 json형테의 데이터를 받아서 vo객체 형태로 변환 시킴) 
	public ResponseEntity<String> register(@RequestBody ReplyVO vo) { 
		
		ResponseEntity<String> entity= null;
		try {
			replyService.addReply(vo);
			entity = new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity= new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	//특정 게시판의 댓글 모두 보기 (@pathVariable로 url에서 필요한 데이터 즉 여기서는 bno를 뽑아와서 (board의 bno의 외래키로 설정) 그 bno로 service,dao를 거쳐서 데에터를 뽑아와서 list에 set
	@RequestMapping(value="/all/{bno}", method=RequestMethod.GET)
	public ResponseEntity<List<ReplyVO>> list(@PathVariable("bno") Integer bno){
		
		ResponseEntity<List<ReplyVO>> entity =null;
		try {
			
			entity=new ResponseEntity<List<ReplyVO>>(replyService.listReply(bno),HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity= new ResponseEntity<List<ReplyVO>>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	//댓글 수정(@PathVariable로 url상의 rno데이터를 받아오고  rno, @RequestBody로  json형태의  데이터를 vo객체로 변환해서 사용 )
	//같은 rno의 내용을 수정하기 위해서 받아오고  vo객체형태로 변환한 데이터를 가지고온걸 service,dao를 이용해서 디비에 set해주면 끝!
	@RequestMapping(value="/{rno}",method= {RequestMethod.PATCH,RequestMethod.PUT})
	public ResponseEntity<String> update(@PathVariable("rno") Integer rno,@RequestBody ReplyVO vo) {
		
		ResponseEntity<String> entity= null;
		try {
			vo.setRno(rno);// rno같은 경우는 auto_increament 이기 때문에 수정할때 rno가 바뀌지 않도록 pathVariable에서 뽑아온rno를 그대로 set해준다. 
			replyService.modifyReply(vo);
			entity= new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	//댓글 삭제 (uri에서 rno 데이터를 가지고와서 그냥 delete하는 부분 )
	@RequestMapping(value="/{rno}",method=RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("rno") Integer rno) {
		
		ResponseEntity<String> entity=null;
		try {
			replyService.removeReply(rno);
			entity=new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity= new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	//페이징처리 (여기서 제일 중요한것은 ajax처리를 했기때문에 model로 보내줄수가 없다는 것이다. 그래서 Map타입으로 따로 객체를 생성해서 사용해야함)
	@RequestMapping(value="/{bno}/{page}",method=RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> listPage(@PathVariable Integer bno,@PathVariable Integer page) {
		
		ResponseEntity<Map<String,Object>> entity = null;
		try {
			Criteria cri= new Criteria();
			//@PathVariable 로 현재  page 번호를 추출해서 가지고와서 set해줌 페이징 유지하기 위해서
			cri.setPage(page); 
			// startPage나 perPage 등 페이징처리에 관한 데이터가 들어있는 pageMaker 에  page,perPageNum에  set해줌
			PageMaker pageMaker= new PageMaker();
			pageMaker.setCri(cri); // page와perPageNum 벙보를 pageMer에 set해줌
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			// 파라미터를 이용해서 얻어온 것을 list에 담아서 map에 담아서 보낸다
			List<ReplyVO> list= replyService.listReplyPage(bno, cri);  //set해준 정보를 가지고 조회해서 나온 데이터를 list에 담아
			map.put("list", list);
			
			//총 페이지수를 얻어와서 pageMaker에 set해놓고 map에 담아서 보냄
			int replyCount=replyService.count(bno); 
			pageMaker.setTotalCount(replyCount);
			map.put("pageMaker", pageMaker);
			 //마지막엔 map에 담겨져 있는것(list와 totalCount)을  entity에 담어서 보내
			entity= new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			
		}catch(Exception e) {
			e.printStackTrace();
			entity= new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
}
