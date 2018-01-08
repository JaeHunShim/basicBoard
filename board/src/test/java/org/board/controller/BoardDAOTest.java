package org.board.controller;

import javax.inject.Inject;

import org.board.domain.BoardVO;
import org.board.persistence.BoardDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class BoardDAOTest {
	
	@Inject
	private BoardDAO dao;
	
	private static Logger logger= LoggerFactory.getLogger(BoardDAOTest.class);
	
	@Test //게시판 등록테스트처리
	public void testInsertBoard() throws Exception{
		
		BoardVO board=new BoardVO();
		board.setContent("테스트코드에서 한번 입력해보겠습니다.");
		board.setTitle("테스트");
		board.setWriter("jaehuniya");
		
		dao.create(board);
	}
	@Test //게시판 안의 내용읽어오기
	public void testRead() throws Exception{
		logger.info(dao.read(5).toString());
	}
	@Test //게시판 수정 테스트
	public void testUpdate() throws Exception{
		
		BoardVO vo = new BoardVO();
		vo.setBno(3);
		vo.setContent("수정해본거");
		vo.setTitle("수정해본거");
		
		dao.update(vo);
	}
	@Test //게시판 내용 삭제 테스트
	public void testDelete() throws Exception{
		
		dao.delete(3);
	}
}
