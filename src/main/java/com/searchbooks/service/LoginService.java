package com.searchbooks.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import com.searchbooks.domain.Entity.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.searchbooks.utils.CookieBox;
import com.searchbooks.utils.CryptEncoding;

@Service
public class LoginService {
	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

	@Autowired
	MemberService memberService;

	@Transactional
	public boolean login(HttpServletResponse res, String account, String pwd) {
		//
		Member member = memberService.getMember(account);
		if (!account.isEmpty() && !pwd.isEmpty()) {
			PasswordEncoder passwordEncoder = new CryptEncoding();
			if (member == null) {
				member = new Member(account, passwordEncoder.encode(pwd), Timestamp.valueOf(LocalDateTime.now()));
				memberService.save(member);
				CookieBox.login(res, account);
				logger.info(account + " 회원가입 / 로그인 완료");
				return true;
			}
			if (member != null) {
				if (passwordEncoder.matches(pwd, member.getPwd())) {
					CookieBox.login(res, account);
					logger.info(account + " 로그인 완료");
					return true;
				}
			}

		}
		logger.info(account + " 로그인 실패");
		return false;
	}

	public String logout(HttpServletResponse res) {
		//
		CookieBox.logout(res);
		return "redirect:/loginForm";
	}

}
