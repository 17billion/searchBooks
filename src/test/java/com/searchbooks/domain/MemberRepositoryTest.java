package com.searchbooks.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.searchbooks.domain.Entity.Member;
import com.searchbooks.domain.repository.MemberRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MemberRepositoryTest {
	@Autowired
	private MemberRepository memberRepository;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testInsertAndSelect() {
		// 1. 회원 저장
		this.memberRepository.save(new Member("test", "test", Timestamp.valueOf(LocalDateTime.now())));

		// 2. 회원 검색
		Member member = this.memberRepository.findById("test").orElse(null);
		assertThat(member.getAccount()).isEqualTo("test");
	}

	@After
	public void tearDown() throws Exception {

	}
}
