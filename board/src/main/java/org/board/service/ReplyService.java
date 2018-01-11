package org.board.service;

import java.util.List;

import org.board.domain.Criteria;
import org.board.domain.ReplyVO;

public interface ReplyService {
	// 조건에 맞는 댓글 불러오기
	public List<ReplyVO> listReply(Integer bno) throws Exception;
	// 댓글 작성
	public void addReply(ReplyVO vo) throws Exception;
	// 댓글 수정
	public void modifyReply(ReplyVO vo) throws Exception;
	// 댓글 삭제
	public void removeReply(Integer rno) throws Exception;
	// 댓글 페이징 처리
	public List<ReplyVO> listReplyPage(Integer bno,Criteria cri) throws Exception;
	// 댓글 총갯수 가지고오기 
	public int count(Integer bno) throws Exception;
}
