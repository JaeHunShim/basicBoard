package org.board.service;

import java.util.Date;

import javax.inject.Inject;

import org.board.domain.UserVO;
import org.board.dto.LoginDTO;
import org.board.persistence.UserDAO;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	@Inject
	UserDAO userDAO;
	
	@Override
	public UserVO login(LoginDTO dto) throws Exception {
		
		return userDAO.login(dto);
	}
	// id로 session 정보와  session 날짜를 업데이트 
	@Override
	public void keepLogin(String uid, String sessionId, Date next) throws Exception {
		
		userDAO.keepLogin(uid, sessionId, next);
		
	}
	// session 정보를 가지고 사용자 정보를 모두 뽑아오는 부분
	@Override
	public UserVO checkLoginBefore(String value) throws Exception {
		
		return userDAO.checkUserWithSessionKey(value);
	}
	@Override
	public void join(UserVO userVO) throws Exception {
		
		userDAO.join(userVO);
	}

}
