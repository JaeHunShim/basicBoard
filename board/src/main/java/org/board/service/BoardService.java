package org.board.service;

import java.util.List;

import org.board.domain.BoardVO;

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
}
