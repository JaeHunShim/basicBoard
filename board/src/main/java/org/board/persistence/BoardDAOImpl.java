package org.board.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.board.domain.BoardVO;
import org.board.domain.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	@Inject
	private SqlSession sqlSession;
	
	private static final String namespace="org.board.mapper.BoardMapper";
	
	
	// 게시판 데이터 삽입
	@Override
	public void create(BoardVO vo) throws Exception {
		sqlSession.insert(namespace+".create",vo);
		
	}
	// 게시판 데이터 가지고오기
	@Override
	public BoardVO read(Integer bno) throws Exception {
		
		return sqlSession.selectOne(namespace+".read",bno);
	}
	// 해당 게시물 수정
	@Override
	public void update(BoardVO vo) throws Exception {
		
		sqlSession.update(namespace+".update",vo);
	}
	// 해당 게시물 삭제
	@Override
	public void delete(Integer bno) throws Exception {
		
		sqlSession.delete(namespace+".delete",bno);
	}
	// 메인 게시판 목록 
	@Override
	public List<BoardVO> listAll() throws Exception {
		
		return sqlSession.selectList(namespace+".listAll");
	}
	// 페이징 처리 쿼리문 관련 
	@Override
	public List<BoardVO> listPage(int page) throws Exception {
		
		if(page<=0) {
			page=1;
		}
		page=(page-1)*10;  //10,20,30씩 증가하게 하기
		
		return sqlSession.selectList(namespace+".listPage", page);
	}
	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		
		return sqlSession.selectList(namespace+".listCriteria", cri);
	} 
	//전체 개시물 즉 totalCount가지고오는 dao
	@Override
	public int countPaging(Criteria cri) throws Exception {
		
		return sqlSession.selectOne(namespace+".countPaging",cri);
	}
	// 댓글 갯수 업데이트 (파라미터가 두개 이상이기때문에 map으로  받아줌)
	@Override
	public void updateReplyCnt(Integer bno, int amount) throws Exception{
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("bno", bno);
		map.put("amount", amount);
		
		sqlSession.update(namespace+".updateReplyCnt", map);
		
	}
	//조회수 증가시시키기
	public void updateViewCnt(Integer bno) throws Exception{
		
		sqlSession.update(namespace+".updateViewCnt", bno);
		
	}
	// 파일 업로드 bno의 경우는 last-insert-id 로 받아오니가 파라미터가 필요없음 
	@Override
	public void addAttach(String fullName) throws Exception {
		
		sqlSession.insert(namespace+".addAttach",fullName);
		
	}
	// 상세피이지에서 파일 업로드 정보 보기 
	@Override
	public List<String> getAttach(Integer bno) throws Exception {
		
		return sqlSession.selectList(namespace+".getAttach", bno);
	}
	//파일삭제(수정하기 위해서)
	@Override
	public void deleteAttach(Integer bno) throws Exception {
		
		sqlSession.delete(namespace+".deleteAttach",bno);
		
	}
	//파일 삭제한후에 다시 insert하는 부분
	@Override
	public void replaceAttach(String fileName, Integer bno) throws Exception {
		
		Map<String,Object> map= new HashMap<String,Object>();
		
		map.put("fileName", fileName);
		map.put("bno", bno);
		
		sqlSession.insert(namespace+".replaceAttach", map);		
	}

}
