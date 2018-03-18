package org.board.service;

import java.util.Date;

import javax.inject.Inject;

import org.board.domain.MailHandler;
import org.board.domain.TempKey;
import org.board.domain.UserVO;
import org.board.dto.LoginDTO;
import org.board.persistence.UserDAO;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserService {
	
	@Inject
	UserDAO userDAO;
	private JavaMailSender mailSender;
	
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
	//회원 가입
	@Override
	public void join(UserVO userVO) throws Exception {
		
		userDAO.join(userVO);
		
		String key = new TempKey().getKey(50,false);
		userDAO.createAuthKey(userVO.getEmail(), key);
		
		MailHandler sendMail = new MailHandler(mailSender);
		
		sendMail.setSubject("[서비스 메일 인증]");
		sendMail.setText(new StringBuffer().append("<h1>메일 인증</h1>")
				.append("<a href='http://localhost/user/emailConfirm?email=")
				.append(userVO.getEmail()).append("&key=")
				.append(key)
				.append("' target='_blenk'>이메일 인증 확인</a>").toString());
		
		sendMail.setFrom("호스트 이메일 아이디", "알몸개발자");
		sendMail.setTo(userVO.getEmail());
		sendMail.send();
	}
	//인증된 이메일 가지고 오기
	@Override
	public void userAuth(String email) throws Exception {
		
		userDAO.userAuth(email);
	}

}
