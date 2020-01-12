package com.searchbooks.service;

import com.searchbooks.domain.Entity.SearchCounting;
import com.searchbooks.domain.Entity.SearchRanking;
import com.searchbooks.domain.repository.SearchCountRepository;
import com.searchbooks.domain.repository.SearchRankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class SearchCountService {
	@Autowired
	SearchCountRepository searchCountRepository;

	@Autowired
	SearchRankingRepository searchRankingRepository;

	@Transactional
	public void increaseWordCount(String search_word) {
		SearchCounting searchCounting = searchCountRepository.findBySearch_word(search_word);
		if (searchCounting == null) {
			searchCountRepository.save(new SearchCounting(search_word, 1L));
			searchCountRepository.increaseWordCounting(search_word);
		} else {
			searchCountRepository.increaseWordCounting(search_word);
		}
	}

	@Transactional
	public void rankingWordProc(String search_word) {
		SearchCounting searchCounting = searchCountRepository.findBySearch_word(search_word);
		List<SearchRanking> searchRankingList = searchRankingRepository.findByAll();
		SearchRanking searchRanking = new SearchRanking(searchCounting.getSearch_word(), searchCounting.getCount());

		if (searchRankingList.size() < 10) {
			searchRankingRepository.save(searchRanking);
		} else {
			Collections.sort(searchRankingList);
			for(SearchRanking sr : searchRankingList){
				if((sr.getSearch_word()).equals(search_word)){
					searchRankingRepository.save(searchRanking);
					return;
				}
			}
			if (searchRankingList.get(searchRankingList.size() - 1).getCount() < searchCounting.getCount()) {
				searchRankingRepository.deleteBySearchword(searchRankingList.get(searchRankingList.size() - 1).getSearch_word());
				searchRankingRepository.save(searchRanking);
			}
		}
	}

	@Transactional
	public List<SearchRanking> rankingWordView() {

		List<SearchRanking> searchRankingList = searchRankingRepository.findByAll();
		Collections.sort(searchRankingList);
		return searchRankingList;

	}
}
