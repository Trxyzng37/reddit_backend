package com.trxyzng.trung.authentication.changepassword;

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
public interface PasscodeRepo extends JpaRepository<PasscodeEntity, Integer> {
    @Query("select t.email from PasscodeEntity t where t.email = :email")
    Optional<String> findEmail(@Param("email") String email);
    @Query("select t.passcode from PasscodeEntity t where t.email = :email")
    int findPasscodeByEmail(@Param("email") String email);
    @Modifying
    @Query("update PasscodeEntity t set t.passcode = :passcode, t.created_at = :created_at where t.email = :email")
    void updatePasscodeByEmail(@Param("email") String email, @Param("passcode") int passcode, @Param("created_at") Instant created_at);
    PasscodeEntity save(PasscodeEntity passcodeEntity);
}
