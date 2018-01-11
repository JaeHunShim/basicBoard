package org.board.persistence;

import java.util.List;

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
}
