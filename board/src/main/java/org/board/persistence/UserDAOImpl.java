package org.board.persistence;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
	// 로그인한 사용자의 sessionkey와 날짜를 업데이트 하는 부분
	@Override
	public void keepLogin(String uid, String sessionId, Date next) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("uid", uid);
		map.put("sessionId", sessionId);
		map.put("next", next);
		
		sqlSession.update(namespace+".keepLogin", map);
		
	}
	//logincookie에 기록된 값으로 사용자 정보 가지고 오기 
	@Override
	public UserVO checkUserWithSessionKey(String value) {
		
		return sqlSession.selectOne(namespace+".checkUserWithSessionKey", value);
	}
	//회원 가입
	@Override
	public void join(UserVO userVO) throws Exception {
		
		sqlSession.insert(namespace+".insertMember", userVO);
	}

}
