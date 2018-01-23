package org.board.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.board.domain.UserVO;
import org.board.dto.LoginDTO;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {
	
	@Inject
	private SqlSession sqlSession;
	
	private static String namespace="org.board.mapper.UserMapper";
	// 회원 정보 가지고오기 (로그인하기 위해서)
	@Override
	public UserVO login(LoginDTO dto) throws Exception {
		
		return sqlSession.selectOne(namespace+".login", dto);
	}

}
