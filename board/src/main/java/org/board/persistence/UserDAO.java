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
}
