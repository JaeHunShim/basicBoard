package org.board.controller;

import javax.inject.Inject;

import org.board.domain.BoardVO;
import org.board.domain.ReplyVO;
import org.board.persistence.BoardDAO;
import org.board.persistence.ReplyDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class ReplyDAOTest {
	@Inject
	private ReplyDAO dao;
	
	private static Logger logger= LoggerFactory.getLogger(BoardDAOTest.class);
	
	@Test //게시판 등록테스트처리
	public void testInsertReply() throws Exception{
		
		ReplyVO vo=new ReplyVO();
		for(int i=1; i<50; i++) { //더미 데이터 넣기 
		vo.setReplytext("댓글 테스트입니다.."+i);
		vo.setReplyer("jaehuniya"+i);
		vo.setBno(2);
		vo.setRno(2);
		
		dao.create(vo);
		}
	}
}
