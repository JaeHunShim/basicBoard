package org.board.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.board.domain.Criteria;
import org.board.domain.ReplyVO;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyDAOImpl implements ReplyDAO {
	
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

	@Override // 두개의 조건으로 가져와야 하기때문에 파라미터를 두개 주고 받아올때는  두개이상이기때문에 map에 담아서 가져옴 
	public List<ReplyVO> listPage(Integer bno,Criteria cri) throws Exception {
		
		Map<String,Object> paramMap= new HashMap<String,Object>();
		
		paramMap.put("bno", bno);
		paramMap.put("cri", cri);
		
		return sqlSession.selectList(namespace+".listPage",paramMap);
	}

	@Override
	public int count(Integer bno) throws Exception {
		
		return sqlSession.selectOne(namespace+".count",bno);
	}
	//댓글 삭제시에 해당 게시물의 번호를 가져오는 부분 
	@Override
	public int getBno(Integer rno) throws Exception {
		
		return sqlSession.selectOne(namespace+".getBno", rno);
	}

}
