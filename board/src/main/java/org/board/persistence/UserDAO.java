package org.board.persistence;

import java.util.Date;

import org.board.domain.UserVO;
import org.board.dto.LoginDTO;

public interface UserDAO {
	
	// 1. 로그인할때 사용자 정보
	public UserVO login(LoginDTO dto) throws Exception;
	// 2. 세션정보 업데이트
	public void keepLogin(String uid, String sessionId,Date next);
	// 3. 쿠키정보 불러오기
	public UserVO checkUserWithSessionKey(String value);
	// 4. 회원 가입
	public void join(UserVO userVO) throws Exception;
	// 5. 이메일 인증
	public void createAuthKey(String email,String authCode) throws Exception;
	// 6. 저장된 이메일 가지고 오기 
	public void userAuth(String email) throws Exception;

}