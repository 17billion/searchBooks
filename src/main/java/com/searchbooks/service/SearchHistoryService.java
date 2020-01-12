package com.searchbooks.service;

import javax.transaction.Transactional;

import com.searchbooks.domain.Entity.Member;
import com.searchbooks.domain.Entity.SearchHistory;
import com.searchbooks.domain.repository.SearchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SearchHistoryService {
	@Autowired
    SearchHistoryRepository searchHistoryRepository;

	@Transactional
	public void save(SearchHistory entity) {
		searchHistoryRepository.save(entity);
	}

	public Page<SearchHistory> findByMember(Member member, Pageable pageable) {
		//
		return searchHistoryRepository.findByMember(member, pageable);
	}

}
