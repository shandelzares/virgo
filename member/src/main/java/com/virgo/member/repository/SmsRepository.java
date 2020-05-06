package com.virgo.member.repository;

import com.virgo.member.model.Sms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SmsRepository extends JpaRepository<Sms, String> {

    Optional<Sms> findFirstByCellphoneAndCompanyCodeAndExpirationTimeAfter(String phone, String companyCode, LocalDateTime now);
}
