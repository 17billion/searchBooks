package com.searchbooks.service;

import static org.junit.Assert.*;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.searchbooks.enums.EnumSearchTarget;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(ApiServiceTest.class);

	@Autowired
	private ApiService apiService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSearchBooks() throws Exception {
		// 1. 카카오 책 검색 API 호출
		Map<String, Object> jsonMap = apiService.kakaoSearchBooks("Spring Security", EnumSearchTarget.전체.getCode(), 1);

		// 2. 검색된 정보 (책 리스트) 확인.
		assertNotNull(jsonMap.get("documents"));

		// 3. 네이버 책 검색 API 호출
		jsonMap = apiService.naverSearchBooks("Spring Security", EnumSearchTarget.전체.getCode(), 1);

		// 4. 검색된 정보 (책 리스트) 확인.
		assertNotNull(jsonMap.get("documents"));

	}
}
