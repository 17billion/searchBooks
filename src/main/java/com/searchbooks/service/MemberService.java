package com.searchbooks.service;

import javax.transaction.Transactional;

import com.searchbooks.domain.Entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.searchbooks.domain.repository.MemberRepository;

@Service
public class MemberService {
	@Autowired
	MemberRepository memberRepository;

	@Transactional
	public Member getMember(String account) {
		return memberRepository.findById(account).orElse(null);
	}

	@Transactional
	public void save(Member member) {
		memberRepository.save(member);
	}

}
