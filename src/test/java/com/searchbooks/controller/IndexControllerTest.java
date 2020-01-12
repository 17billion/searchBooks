package com.searchbooks.controller;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.servlet.http.Cookie;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.searchbooks.domain.Entity.Member;
import com.searchbooks.service.MemberService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(IndexControllerTest.class);

	@Autowired
	private RequestMappingHandlerAdapter handlerAdapter;
	@Autowired
	private RequestMappingHandlerMapping handlerMapping;

	@MockBean
	private MemberService memberService;

	private MockHttpServletRequest req;
	private MockHttpServletResponse res;

	@Before
	public void setUp() throws Exception {

		Member member = new Member("test", "test", Timestamp.valueOf(LocalDateTime.now()));
		given(memberService.getMember(member.getAccount())).willReturn(member);


		req = new MockHttpServletRequest();
		res = new MockHttpServletResponse();
		req.setCookies(new Cookie("account_site", member.getAccount()));

	}

	@Test
	public void testIndex() throws Exception {

		req.setMethod("get");
		req.setRequestURI("/");

		Object handler = handlerMapping.getHandler(req).getHandler();
		ModelAndView mav = handlerAdapter.handle(req, res, handler);
		assertEquals(res.getStatus(), 200);
		assertThat(mav.getViewName(), CoreMatchers.is("/index"));

	}

	@Test
	public void testDetail() throws Exception {

		req.setMethod("get");
		req.setRequestURI("/detail");

		req.addParameter("isbn", "12341234");

		Object handler = handlerMapping.getHandler(req).getHandler();
		ModelAndView mav = handlerAdapter.handle(req, res, handler);
		assertEquals(res.getStatus(), 200);
		assertThat(mav.getViewName(), CoreMatchers.is("/detail"));
	}

	@Test
	public void testSearchHistory() throws Exception {

		req.setMethod("get");
		req.setRequestURI("/searchHistory");

		Object handler = handlerMapping.getHandler(req).getHandler();
		ModelAndView mav = handlerAdapter.handle(req, res, handler);
		assertEquals(res.getStatus(), 200);

		assertThat(mav.getViewName(), CoreMatchers.is("/searchHistory"));
	}

	@After
	public void tearDown() throws Exception {
	}
}
