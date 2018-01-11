package org.board.service;

import java.util.List;

import javax.inject.Inject;

import org.board.domain.ReplyVO;
import org.board.persistence.ReplyDAO;
import org.springframework.stereotype.Service;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Inject
	ReplyDAO dao;

	@Override
	public List<ReplyVO> listReply(Integer bno) throws Exception {
		
		return dao.list(bno);
	}

	@Override
	public void addReply(ReplyVO vo) throws Exception {
		
		dao.create(vo);
	}

	@Override
	public void modifyReply(ReplyVO vo) throws Exception {
		
		dao.update(vo);
	}

	@Override
	public void removeReply(Integer rno) throws Exception {
		
		dao.remove(rno);
	}

}
