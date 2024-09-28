package com.trxyzng.trung.authentication.signup.usernamepassword.confirmEmail;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Transactional
@Repository
public interface ConfirmEmailPasscodeRepo extends JpaRepository<ConfirmEmailPasscodeEntity, Integer> {
    @Query("select t.email from ConfirmEmailPasscodeEntity t where t.email = :email")
    Optional<String> findEmailByEmail(@Param("email") String email);
    @Query("select t.passcode from ConfirmEmailPasscodeEntity t where t.email = :email")
    int findPasscodeByEmail(@Param("email") String email);
    @Query("select t.expiration_at from ConfirmEmailPasscodeEntity t where t.email = :email")
    Instant findExpirationAtByEmail(@Param("email") String email);
    @Query("select t.created_at from ConfirmEmailPasscodeEntity t where t.email = :email")
    Instant findCreatedAtByEmail(@Param("email") String email);
    @Modifying
    @Query("update ConfirmEmailPasscodeEntity t set t.passcode = :passcode, t.created_at = :created_at, t.expiration_at = :expired_at where t.email = :email")
    void updatePasscodeByEmail(@Param("email") String email, @Param("passcode") int passcode, @Param("created_at") Instant created_at, @Param("expired_at") Instant expired_at);

    ConfirmEmailPasscodeEntity save(ConfirmEmailPasscodeEntity confirmEmailPasscodeEntity);
}
