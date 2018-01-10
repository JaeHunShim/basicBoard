package org.board.controller;

import java.util.List;

import javax.inject.Inject;

import org.board.domain.BoardVO;
import org.board.domain.Criteria;
import org.board.persistence.BoardDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class BoardDAOTest {
	
	@Inject
	private BoardDAO dao;
	
	private static Logger logger= LoggerFactory.getLogger(BoardDAOTest.class);
	
	@Test //게시판 등록테스트처리
	public void testInsertBoard() throws Exception{
		
		BoardVO board=new BoardVO();
		for(int i=0; i<1000; i++) { //더미 데이터 넣기 
		board.setContent("테스트코드에서 한번 입력해보겠습니다.");
		board.setTitle("테스트");
		board.setWriter("jaehuniya");
		
		dao.create(board);
		}
	}
	@Test //게시판 안의 내용읽어오기
	public void testRead() throws Exception{
		logger.info(dao.read(100).toString());
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
	@Test	//페이지마다 제대로 데이터 뽑아오는지 쿼리문 테스트
	public void testListPage() throws Exception{
		
		int page=3;
		
		List<BoardVO> list=dao.listPage(page);
		
		for(BoardVO boardVO:list) {
			logger.info(boardVO.getBno()+":"+boardVO.getTitle());
		}
	}
	@Test //페이징 처리 테스트
	public void testListCriteria()throws Exception{
		
		Criteria cri = new Criteria();
		cri.setPage(2);
		cri.setPerPageNum(20);
		
		List<BoardVO> list=dao.listCriteria(cri);
		
		for(BoardVO boardVO:list) {
			logger.info(boardVO.getBno()+":"+boardVO.getTitle());
		}
	}
	@Test //uri 불로오는 테스트(uriComponents 클래스는 path나 query에 해당하는 문장열을 추가해서 원하는 uri를 생성할때 사용하는 용도)
	public void testURI() throws Exception{
		UriComponents uriComponents=UriComponentsBuilder.newInstance()
				.path("/board/read")
				.queryParam("bno", 12)
				.queryParam("perPageNum", 20)
				.build();
		logger.info("/board/read?bno=12&perPageNum=20");
		logger.info(uriComponents.toString());
	}
	@Test //uri 불로오는 테스트(uriComponents 클래스는 path나 query에 해당하는 문장열을 추가해서 원하는 uri를 생성할때 사용하는 용도 즉 원하는 정보를 uri에 실어서 보낼수 있다.)
	public void testURI2() throws Exception{
		UriComponents uriComponents=UriComponentsBuilder.newInstance()
				.path("/board/read")
				.queryParam("bno", 12)
				.queryParam("perPageNum", 20)
				.build()
				.expand("board","read")
				.encode();
		logger.info("/board/read?bno=12&perPageNum=20");
		logger.info(uriComponents.toString());
	}
}
