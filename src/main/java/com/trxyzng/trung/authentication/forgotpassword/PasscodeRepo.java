package com.trxyzng.trung.authentication.forgotpassword;

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
//    @Query("select t from PasscodeEntity t where t.email = :email")
    Optional<PasscodeEntity> findByEmail(String email);
    void deleteAllByEmail(String email);
//    PasscodeEntity save(PasscodeEntity passcodeEntity);
    @Modifying
    @Query("update PasscodeEntity t set t.passcode = :passcode, t.created_at = :created_at where t.email = :email")
    void updatePasscodeByEmail(@Param("email") String email, @Param("passcode") int passcode, @Param("created_at") Instant created_at);

}
