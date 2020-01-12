package com.searchbooks.domain.repository;

import com.searchbooks.domain.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {

}
