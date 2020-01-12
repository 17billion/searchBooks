package com.searchbooks.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.searchbooks.domain.Entity.Member;
import com.searchbooks.domain.Entity.SearchHistory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SearchHistoryRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private com.searchbooks.domain.repository.SearchHistoryRepository SearchHistoryRepository;

	private Member member;

	@Before
	public void setUp() throws Exception {
		member = new Member("test", "test", Timestamp.valueOf(LocalDateTime.now()));
		// this.memberRepository.save(member);
		this.entityManager.persist(member);
	}

	@Test
	public void testInsertSelectAndDelete() {
		// 1. 검색 히스토리 저장
		SearchHistory searchHistory = new SearchHistory("Spring Security", Timestamp.valueOf(LocalDateTime.now()), member);
		this.SearchHistoryRepository.save(searchHistory);

		// 2. 회원으로 검색
		assertTrue(this.SearchHistoryRepository.findByMember(member, new PageRequest(0, 1)).getContent().size() > 0);

		// 3. 삭제 테스트
		this.SearchHistoryRepository.deleteById(this.SearchHistoryRepository.findByMember(member, new PageRequest(0, 1)).getTotalElements());

		// 4. 삭제된것 확인
		assertFalse(this.SearchHistoryRepository.findByMember(member, new PageRequest(0, 1)).getContent().size() > 0);

	}

	@After
	public void tearDown() throws Exception {

	}
}
