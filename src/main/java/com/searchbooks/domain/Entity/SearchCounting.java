package com.searchbooks.domain.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@ToString
@Table(name = "search_counting")
public class SearchCounting {

	@Id
	@Column(nullable = false)
	private String search_word;

	@Column(nullable = false)
	private Long count;

	public SearchCounting() {
		super();

	}
	public SearchCounting(String search_word, Long count) {
		super();
		this.search_word = search_word;
		this.count = count;
	}

}
