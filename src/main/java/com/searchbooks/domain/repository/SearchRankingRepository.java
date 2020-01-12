package com.searchbooks.domain.repository;

import com.searchbooks.domain.Entity.SearchRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SearchRankingRepository extends JpaRepository<SearchRanking, Long> {

	@Query("SELECT s FROM SearchRanking s")
	List<SearchRanking> findByAll();

	@Modifying
	@Transactional
	@Query("DELETE FROM SearchRanking s where s.search_word=?1")
	void deleteBySearchword(String search_word);

}
