package com.searchbooks.domain.Entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "member")
public class Member {

	@Id
	private String account;

	@JsonIgnore
	@Column(nullable = false)
	private String pwd;

	@Column(nullable = false)
	private Timestamp regdate;

	public Member() {
		super();
	}

	public Member(String account, String pwd, Timestamp regdate) {
		super();
		this.account = account;
		this.pwd = pwd;
		this.regdate = regdate;
	}

}
