package com.searchbooks.domain.Entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "search_history")
public class SearchHistory {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String search_word;

	@Column(nullable = false)
	private Timestamp regdate;

	@ManyToOne(optional = false)
	@JoinColumn(name = "member_account")
	private Member member;

	public SearchHistory() {
		super();

	}

	public SearchHistory(String search_word, Timestamp regdate, Member member) {
		super();
		this.search_word = search_word;
		this.regdate = regdate;
		this.member = member;
	}

}
