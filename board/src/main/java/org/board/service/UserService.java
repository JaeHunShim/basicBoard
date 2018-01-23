package org.board.service;

import org.board.domain.UserVO;
import org.board.dto.LoginDTO;

public interface UserService {
	
	public UserVO login(LoginDTO dto) throws Exception;
}
