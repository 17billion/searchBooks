package com.searchbooks.controller;

import javax.servlet.http.HttpServletRequest;

import com.searchbooks.domain.Entity.Member;
import com.searchbooks.domain.Entity.SearchHistory;
import com.searchbooks.enums.EnumSearchTarget;
import com.searchbooks.exceptions.IllegalLoginException;
import com.searchbooks.service.MemberService;
import com.searchbooks.service.SearchHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.searchbooks.utils.CookieBox;
@Controller
@EnableAutoConfiguration
public class IndexController {
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
    MemberService memberService;


	@Autowired
    SearchHistoryService searchHistoryService;

	private Member getMemberObj(HttpServletRequest req) throws Exception {
		String account = CookieBox.getAccount(req);
		Member member = memberService.getMember(account);
		if (member == null) {
			throw new IllegalLoginException("로그인 되지 않았습니다.");
		}
		return member;
	}

	@RequestMapping("/")
	public String index(Model model) {
		try {
			model.addAttribute("EnumTarget", EnumSearchTarget.values());
			return "/index";
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "redirect:/loginForm";
		}

	}

	@RequestMapping("/detail")
	public String detail() {
		try {
			return "/detail";
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "redirect:/loginForm";
		}
	}


	@RequestMapping("/searchHistory")
	public String searchHistory(HttpServletRequest req, Model model,
			@PageableDefault(size = 10, page = 0, sort = "regdate", direction = Direction.DESC) Pageable pageable) {
		try {
			Member member = getMemberObj(req);
			Page<SearchHistory> searchHistoryPage = searchHistoryService.findByMember(member, pageable);
			model.addAttribute("searchHistoryPage", searchHistoryPage);
			return "/searchHistory";
		} catch (Exception e) {
			logger.info(e.getMessage());
			return "redirect:/loginForm";
		}

	}

}
