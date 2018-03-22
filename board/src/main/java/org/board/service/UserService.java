package org.board.service;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.board.domain.UserVO;
import org.board.dto.LoginDTO;

public interface UserService {
	
	public UserVO login(LoginDTO dto) throws Exception;
	// id로 session 정보와  session 날짜를 업데이트 
	public void keepLogin(String uid,String sessionId, Date next) throws Exception;
	// session 정보를 가지고 사용자 정보를 모두 뽑아오는 부분
	public UserVO checkLoginBefore(String value) throws Exception;
	// 회원 가입
	public void join(UserVO userVO) throws Exception;
	// 인증이메일 받아오기
	public void userAuth(String email) throws Exception;

}
