package com.searchbooks.service;

import static org.junit.Assert.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.searchbooks.domain.Entity.Member;
import com.searchbooks.domain.Entity.SearchHistory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchHistoryServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(SearchHistoryServiceTest.class);

	@Autowired
	private MemberService memberService;

	@Autowired
	private SearchHistoryService searchHistoryService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testInsertSelect() throws Exception {
		Member member = new Member("testMember", "1234", Timestamp.valueOf(LocalDateTime.now()));
		memberService.save(member);

		// 1. 검색 히스토리 등록
		searchHistoryService.save(new SearchHistory("Spring Security", Timestamp.valueOf(LocalDateTime.now()), member));

		// 2. 검색 히스토리 가져오기
		Page<SearchHistory> pageSearchHistory = searchHistoryService.findByMember(member, new PageRequest(0, 10));
		assertNotNull(pageSearchHistory);

	}

}
