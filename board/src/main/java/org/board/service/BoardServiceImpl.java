package org.board.service;


import java.util.List;
import javax.inject.Inject;
import org.board.domain.BoardVO;
import org.board.domain.Criteria;
import org.board.persistence.BoardDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Inject
	private BoardDAO boardDAO;

	
	@Override
	public void regist(BoardVO vo) throws Exception {
		boardDAO.create(vo);
	}
	//READ_COMMITTED는 커밋되지 않은 데이터는 볼수없도록 하는 방법으로 제일 기본적인  사용방법이라고 할수 있다. 
	@Transactional(isolation=Isolation.READ_COMMITTED)
	@Override
	public BoardVO read(Integer bno) throws Exception {
		
		boardDAO.updateViewCnt(bno);
		return boardDAO.read(bno);
	}

	@Override
	public void modify(BoardVO vo) throws Exception {
		
		boardDAO.update(vo);
	}

	@Override
	public void remove(Integer bno) throws Exception {
		
		boardDAO.delete(bno);
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		
		return boardDAO.listAll();
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		
		return boardDAO.listCriteria(cri);
	}

	@Override
	public int listCountCriteria(Criteria cri) throws Exception {
		
		return boardDAO.countPaging(cri);
	}

}
