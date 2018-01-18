package org.board.service;

import java.util.List;

import javax.inject.Inject;

import org.board.domain.Criteria;
import org.board.domain.ReplyVO;
import org.board.persistence.BoardDAO;
import org.board.persistence.ReplyDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Inject
	ReplyDAO replyDAO;
	
	@Inject //댓글수를 증가시키기 위해서 사용
	BoardDAO boardDAO;
	
	@Override
	public List<ReplyVO> listReply(Integer bno) throws Exception {
		
		return replyDAO.list(bno);
	}
	
	@Transactional
	@Override
	public void addReply(ReplyVO vo) throws Exception {

		replyDAO.create(vo);
		boardDAO.updateReplyCnt(vo.getBno(), 1); //글이 작성되면  보여지는 해당 게시물의 댓글도 같이 추가 
	}
	@Transactional
	@Override
	public void modifyReply(ReplyVO vo) throws Exception {
		
		replyDAO.update(vo);
	}

	@Override
	public void removeReply(Integer rno) throws Exception {
		
		int bno = replyDAO.getBno(rno);
		replyDAO.remove(rno);
		boardDAO.updateReplyCnt(bno, -1); //삭제되면 보여지는 해당 댓글의수를 하나 감소 
	}

	@Override
	public List<ReplyVO> listReplyPage(Integer bno, Criteria cri) throws Exception {
		
		return replyDAO.listPage(bno, cri);
	}

	@Override
	public int count(Integer bno) throws Exception {
		
		return replyDAO.count(bno);
	}

}
