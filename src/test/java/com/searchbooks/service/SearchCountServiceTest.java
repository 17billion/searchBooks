package com.searchbooks.service;

import com.searchbooks.domain.Entity.SearchRanking;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchCountServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(SearchCountServiceTest.class);

	@Autowired
	private SearchCountService searchCountService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testInsertSelect() throws Exception {

		//1. 검색어 카운팅 및 랭킹 연산
		searchCountService.increaseWordCount("Spring Security Test");
		searchCountService.rankingWordProc("Spring Security Test");
		searchCountService.increaseWordCount("Spring Security");
		searchCountService.increaseWordCount("Spring Security");
		searchCountService.increaseWordCount("Spring Security");
		searchCountService.rankingWordProc("Spring Security");

		//2. 구성된 랭킹 조회
		List<SearchRanking> SearchRankingList = searchCountService.rankingWordView();
		assertThat(SearchRankingList.size()).isEqualTo(2);
		assertThat(SearchRankingList.get(1).getSearch_word()).isEqualTo("Spring Security Test");
	}

}
