package com.searchbooks.domain;

import com.searchbooks.domain.Entity.SearchCounting;
import com.searchbooks.domain.repository.SearchCountRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SearchCountingRepositoryTest {
	@Autowired
	private SearchCountRepository searchCountRepository;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testInsertAndSelect() {
		// 1. 검색어 카운팅
		searchCountRepository.save(new SearchCounting("Spring Security", 1L));

		// 2. 검색어 카운팅
		searchCountRepository.increaseWordCounting("Spring Security");

		//3. 카운팅 확인
		SearchCounting searchCount = searchCountRepository.findBySearch_word("Spring Security");
		assertThat(searchCount.getSearch_word()).isEqualTo("Spring Security");
	}

	@After
	public void tearDown() throws Exception {

	}
}
