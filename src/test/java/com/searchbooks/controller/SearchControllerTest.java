package com.searchbooks.controller;
;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import com.searchbooks.enums.EnumSearchTarget;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(SearchControllerTest.class);

	@Autowired
	private SearchController searchController;

	private MockHttpServletRequest req;

	@Before
	public void setUp() throws Exception {
		//
		req = new MockHttpServletRequest();

	}

	@Test
	public void testSearchBooks() throws Exception {
		Map<String, Object> result = searchController.searchBooks(req, "test", EnumSearchTarget.전체.getCode(), 1);
		assertNotNull(result.get("documents"));
		logger.info("### result = " + result);

	}

	@Test
	public void testGetBookinfo() throws Exception {
		Map<String, Object> result = searchController.getBookinfo("9791158390785");
		assertNotNull(result.get("documents"));
		logger.info("### result = " + result);
	}


}
