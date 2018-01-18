package org.board.persistence;

import java.util.List;

import org.board.domain.Criteria;
import org.board.domain.ReplyVO;

public interface ReplyDAO {
	//댓글 작성
	public void create(ReplyVO vo) throws Exception;
	// 댓글 수정
	public void update(ReplyVO vo) throws Exception;
	// 댓글 삭제
	public void remove(Integer rno) throws Exception;
	// 댓글 목록
	public List<ReplyVO> list(Integer bno) throws Exception;
	// 댓글 페이징 처리 (기존 페이징 처리와 동일)
	public List<ReplyVO> listPage(Integer bno,Criteria cri) throws Exception;
	// 댓글 총 갯수 세기
	public int count(Integer bno) throws Exception;
	//해당 게시물 번호를 가지고 오는 부분 (댓글이 삭제 될때 해당 게시물의 번호를 가져오기위해서 사용)
	public int getBno(Integer rno) throws Exception;
}
