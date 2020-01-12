package com.searchbooks.domain.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "search_ranking")
public class SearchRanking implements Comparable<SearchRanking> {

	@Id
	@Column(nullable = false)
	private String search_word;

	@Column(nullable = false)
	private long count;

	public SearchRanking() {
		super();

	}
	public SearchRanking(String search_word, Long count) {
		super();
		this.search_word = search_word;
		this.count = count;
	}


	@Override
	public int compareTo(SearchRanking searchRanking) {
		if (searchRanking.count == this.count) return 0;
		return searchRanking.count  > this.count? 1 : -1;
	}


}
