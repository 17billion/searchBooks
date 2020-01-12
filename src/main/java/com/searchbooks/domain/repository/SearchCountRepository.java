package com.searchbooks.domain.repository;

import com.searchbooks.domain.Entity.SearchCounting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SearchCountRepository extends JpaRepository<SearchCounting, Long> {

	@Query("SELECT s FROM SearchCounting s WHERE s.search_word=?1")
	SearchCounting findBySearch_word(String search_word);

	@Modifying
	@Transactional
	@Query("UPDATE SearchCounting s SET s.count = s.count + 1 WHERE s.search_word =?1")
	Integer increaseWordCounting(String search_word);

}
