package org.board.controller;

import javax.inject.Inject;

import org.board.domain.BoardVO;
import org.board.service.BoardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class BoardServiceTest {

	@Inject
	BoardService service;
	
	private static Logger logger= LoggerFactory.getLogger(BoardDAOTest.class);
	
	@Test // 게시물 삽입
	public void testregit() throws Exception{
		
		BoardVO vo=new BoardVO();
		vo.setContent("테스트코드에서 한번 입력해보겠습니다.");
		vo.setTitle("테스트");
		vo.setWriter("jaehuniya");
		
		service.regist(vo);
		
	}
	@Test	//조건에 맞는 게시물 가지고 오기
	public void testRead() throws Exception{
		logger.info(service.read(5).toString());
	}
	@Test //게시판 수정 테스트
	public void testModify() throws Exception{
		
		BoardVO vo = new BoardVO();
		vo.setBno(4);
		vo.setContent("수정해본거");
		vo.setTitle("수정해본거");
		
		service.modify(vo);
	}
	@Test //게시판 내용 삭제 테스트
	public void testDelete() throws Exception{
		
		service.remove(4);
	}
}
