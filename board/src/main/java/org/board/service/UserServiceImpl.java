package org.board.service;

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

}
