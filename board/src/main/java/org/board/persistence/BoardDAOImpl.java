package org.board.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.board.domain.BoardVO;
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

}
