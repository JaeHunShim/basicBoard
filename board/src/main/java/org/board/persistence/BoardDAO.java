package org.board.persistence;

import java.util.List;

import org.board.domain.BoardVO;
import org.board.domain.Criteria;

public interface BoardDAO {
	// 게시판 데이터 삽입
	public void create(BoardVO vo) throws Exception;
	// 해당 게시물 내용가지고 오기
	public BoardVO read(Integer bno) throws Exception;
	// 해당 게시물 수정
	public void update(BoardVO vo) throws Exception;
	// 해당 게시물 삭제
	public void delete(Integer bno) throws Exception;
	// 메인 게시판 목록 
	public List<BoardVO>listAll() throws Exception;
	//페이징 처리관련 기능
	public List<BoardVO>listPage(int page) throws Exception;
	//BoardDAO에  리스트를 출력하는 부분
	public List<BoardVO> listCriteria(Criteria cri) throws Exception;
	//전체 개시물 즉 totalCount가지고오는 dao
	public int countPaging(Criteria cri) throws Exception;
}
