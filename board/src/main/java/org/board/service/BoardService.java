package org.board.service;

import java.util.List;

import org.board.domain.BoardVO;
import org.board.domain.Criteria;

public interface BoardService {
	//게시물 등록
	public void regist(BoardVO vo) throws Exception;
	//게시물 자세히보기
	public BoardVO read(Integer bno) throws Exception;
	//게시물 수정 비지니스로직
	public void modify(BoardVO vo) throws Exception;
	//게시물 제거 비지니스로직
	public void remove(Integer bno) throws Exception;
	//게시물 목록 
	public List<BoardVO> listAll() throws Exception;
	//페이징 처리 
	public List<BoardVO> listCriteria(Criteria cri) throws Exception;
	//전체 개시물 숫자 구하기
	public int listCountCriteria(Criteria cri) throws Exception;
	
}
