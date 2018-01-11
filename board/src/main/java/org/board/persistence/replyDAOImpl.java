package org.board.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.board.domain.ReplyVO;
import org.springframework.stereotype.Repository;

@Repository
public class replyDAOImpl implements ReplyDAO {
	
	@Inject
	SqlSession sqlSession;
	
	private static String namespace="org.board.mapper.replyMapper";
	
	@Override
	public void create(ReplyVO vo) throws Exception {
		
		sqlSession.insert(namespace+".create",vo);
	}

	@Override
	public void update(ReplyVO vo) throws Exception {
		
		sqlSession.update(namespace+".update",vo);
	}

	@Override
	public void remove(Integer rno) throws Exception {
		
		sqlSession.delete(namespace+".delete",rno);
	}

	@Override
	public List<ReplyVO> list(Integer bno) throws Exception {
		
		return sqlSession.selectList(namespace+".list", bno);
	}

}
