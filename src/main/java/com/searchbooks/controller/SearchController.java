package com.searchbooks.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.searchbooks.domain.Entity.SearchRanking;
import com.searchbooks.service.SearchCountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import com.searchbooks.domain.Entity.Member;
import com.searchbooks.domain.Entity.SearchHistory;
import com.searchbooks.enums.EnumSearchTarget;
import com.searchbooks.service.ApiService;
import com.searchbooks.service.MemberService;
import com.searchbooks.service.SearchHistoryService;
import com.searchbooks.utils.CookieBox;

@RestController
@RequestMapping("search/")
public class SearchController {
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

	@Autowired
	ApiService apiService;

	@Autowired
	MemberService memberService;


	@Autowired
	SearchHistoryService searchHistoryService;

	@Autowired
	SearchCountService searchCountService;

	/**
	 * 책 검색 restAPI
	 * 
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/searchBooks")
	public Map<String, Object> searchBooks(HttpServletRequest req,
			@RequestParam("searchWord") String searchWord,
			@RequestParam(name = "target", defaultValue = "all") String target,
			@RequestParam(name = "page", defaultValue = "1") int page) {

		String account = CookieBox.getAccount(req);
		Member member = memberService.getMember(account);
		Map<String, Object> result = apiService.kakaoSearchBooks(searchWord, target, page);

		if(result == null){
			result = apiService.naverSearchBooks(searchWord, target, page);
		}

		searchCountService.increaseWordCount(searchWord);
		searchCountService.rankingWordProc(searchWord);
		searchHistoryService
				.save(new SearchHistory(searchWord, Timestamp.valueOf(LocalDateTime.now()), member));

		return result;
	}

	/**
	 * 책 정보 restAPI
	 * 
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getBookinfo/isbn/{isbn}", method = RequestMethod.GET)
	public Map<String, Object> getBookinfo(@PathVariable String isbn) {

		Map<String, Object> result = apiService.kakaoSearchBooks(isbn, EnumSearchTarget.ISBN.getCode(), 1);
		if(result == null){
			result = apiService.naverSearchBooks(isbn, EnumSearchTarget.ISBN.getCode(), 1);
		}

		return result;
	}

	/**
	 * 랭킹 조회 restAPI
	 *
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/viewRanking")
	public List<SearchRanking>  viewRanking() {
		try {
			List<SearchRanking> searchRankingList = searchCountService.rankingWordView();
			if (searchRankingList == null) {
				return new ArrayList<SearchRanking>();
			}
			return searchRankingList;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return new ArrayList<SearchRanking>();
		}
	}
}
