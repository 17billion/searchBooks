package com.searchbooks.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

import com.searchbooks.utils.CryptEncoding;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(LoginServiceTest.class);

	private MockHttpServletRequest req;
	private MockHttpServletResponse res;

	@Autowired
	private LoginService loginService;

	@Before
	public void setUp() throws Exception {
		req = new MockHttpServletRequest();
		res = new MockHttpServletResponse();

	}

	@Test
	public void testLogin() {
		CryptEncoding encoding = new CryptEncoding();
		// 1. 로그인
		boolean result = loginService.login(res, "testMember", encoding.encode("1234"));
		logger.info("islogined =" + result);

		// 2. 로그아웃
		String redirectUrl = loginService.logout(res);
		assertEquals("redirect:/loginForm", redirectUrl);

	}

}
