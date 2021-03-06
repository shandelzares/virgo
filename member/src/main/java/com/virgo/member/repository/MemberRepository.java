package com.virgo.member.repository;

import com.virgo.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByPhoneAndCompanyCodeAndDeletedIsFalse(String phone,String companyCode);
    Optional<Member> findByMemberId(String memberId);
}
