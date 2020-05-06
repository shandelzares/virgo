package com.virgo.member.repository;

import com.virgo.member.model.InvitationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvitationCodeRepository extends JpaRepository<InvitationCode, Long> {

    Optional<InvitationCode> findByOwnerAndCompanyCode(String owner, String companyCode);
    Optional<InvitationCode> findByCodeAndCompanyCode(String code, String companyCode);
}
