package com.searchbooks.domain;

import com.searchbooks.domain.Entity.SearchCounting;
import com.searchbooks.domain.Entity.SearchRanking;
import com.searchbooks.domain.repository.SearchCountRepository;
import com.searchbooks.domain.repository.SearchRankingRepository;
import com.searchbooks.service.SearchCountService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SearchRankingRepositoryTest {
	@Autowired
	private SearchRankingRepository searchRankingRepository;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testInsertAndSelect() {

		// 1. 랭킹 입력
		searchRankingRepository.save(new SearchRanking("Spring Security", 2l));
		searchRankingRepository.save(new SearchRanking("Spring Security Test", 1l));

		//2. 랭킹 조회
		List<SearchRanking> searchRankingList = searchRankingRepository.findByAll();
		assertThat(searchRankingList.size()).isEqualTo(2);

	}

	@After
	public void tearDown() throws Exception {

	}
}
