package org.board.persistence;

import org.board.domain.UserVO;
import org.board.dto.LoginDTO;

public interface UserDAO {
	
	public UserVO login(LoginDTO dto) throws Exception;
}
