package com.vmo.repository;

import com.vmo.models.entities.EmailConfirmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EmailConfirmTokenRepository extends JpaRepository<EmailConfirmToken, Integer> {
    Optional<EmailConfirmToken> findBytoken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE EmailConfirmToken c SET c.confirmedAt = ?2 WHERE c.token = ?1")
    void updateConfirmedAt(String token, LocalDateTime localDateTime);
}
