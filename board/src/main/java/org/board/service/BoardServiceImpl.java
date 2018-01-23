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

	@Transactional
	@Override
	public void regist(BoardVO vo) throws Exception {
		boardDAO.create(vo);
		//추가부분(파일 업로드정보)
		//1. 우선 vo객체에 file정보를 가지고와서(배열을 쓴 이유는 여러개의 파일이 존재 할수 있기때문에) 
		String[] files=vo.getFiles();
		//2. 파일이 없으면 그냥 그대로 리턴
		if(files == null) {
			return;
		//3. 파일이 있으면 파일이 있는만큼 배열에 있는 파일만큼 반복문을 돌려서 addAttach해주는 부분 
		}else {
			for(String fileName:files) {
				boardDAO.addAttach(fileName);
			}
		}
	}
	//READ_COMMITTED는 커밋되지 않은 데이터는 볼수없도록 하는 방법으로 제일 기본적인  사용방법이라고 할수 있다. 
	@Transactional(isolation=Isolation.READ_COMMITTED)
	@Override
	public BoardVO read(Integer bno) throws Exception {
		
		boardDAO.updateViewCnt(bno);
		return boardDAO.read(bno);
	}
	//게시판 수정하는 부분(파일 처리까지 같이 처리)
	@Transactional
	@Override
	public void modify(BoardVO vo) throws Exception {
		
		boardDAO.update(vo);
		// 1. 현재 bno를 가지고와서 
		Integer bno =vo.getBno();
		// 2. 해당 bno의 파일목록을 삭제하고
		boardDAO.deleteAttach(bno);
		// 여기서부터는 register할때랑 똑같이 다른점은 bno를 받아온다는 점 
		String[] files = vo.getFiles();
		
		if(files==null) {
			return;
		}	
		for(String fileName:files) {
				boardDAO.replaceAttach(fileName, bno);
		}	
	}
	//게시물 삭제 할때 업로드외어있는 파일도 삭제 (데이터 베이스 내에서)
	@Transactional
	@Override
	public void remove(Integer bno) throws Exception {
		
		boardDAO.deleteAttach(bno);
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
	//상세정보에서 업로드 정보보기
	@Override
	public List<String> getAttech(Integer bno) throws Exception {
		
		return boardDAO.getAttach(bno);
	}

}
