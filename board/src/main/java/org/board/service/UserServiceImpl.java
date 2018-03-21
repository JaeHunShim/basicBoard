package org.board.service;

import java.io.PrintWriter;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.board.domain.UserVO;
import org.board.dto.LoginDTO;
import org.board.persistence.UserDAO;
import org.board.util.MailHandler;
import org.board.util.TempKey;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class UserServiceImpl implements UserService {
	
	@Inject
	UserDAO userDAO;
	@Inject
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
	@Transactional
	@Override
	public void join(UserVO userVO) throws Exception {
		//회원가입
		userDAO.join(userVO);
		
		//인증키 생성
		String key = new TempKey().getKey(50,false);
		//인증키를 DB에 저장 
		userDAO.createAuthKey(userVO.getEmail(), key);
		// 메일 보내기 
		MailHandler sendMail = new MailHandler(mailSender);
		sendMail.setSubject("[서비스 메일 인증]");
		sendMail.setText(new StringBuffer().append("<h1>메일 인증</h1>")
				.append("<a href='http://localhost:8080/user/emailConfirm?email=")
				.append(userVO.getEmail())
				.append("&key=").append(key)
				.append("' target='_blenk'>이메일 인증 확인</a>").toString());
		sendMail.setFrom("jaehuniya@gamil.com", "관리자");
		sendMail.setTo(userVO.getEmail());
		sendMail.send();
	}
	//인증된 이메일 확인하면 verity값 바꾸기
	@Override
	public void userAuth(String email) throws Exception {
		userDAO.userAuth(email);
	}

}
