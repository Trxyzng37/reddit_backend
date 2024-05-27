package com.trxyzng.trung.authentication.changepassword.checkpasscode;

import com.trxyzng.trung.authentication.shared.passcode.PasscodeEntity;
import com.trxyzng.trung.authentication.signup.usernamepassword.confirmEmail.ConfirmEmailPasscodeEntity;
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
public interface ChangePasswordPasscodeRepo extends JpaRepository<ChangePasswordPasscodeEntity, Integer> {
    @Query("select case when count(t) > 0 then 1 else 0 end from ChangePasswordPasscodeEntity t where t.email = :email")
    int isEmaillExist(@Param("email") String email);

    @Query("select t.passcode from ChangePasswordPasscodeEntity t where t.email = :email")
    int findPasscodeByEmail(String email);

    @Query("select t.expiration_at from ChangePasswordPasscodeEntity t where t.email = :email")
    Instant findExpirationAtByEmail(@Param("email") String email);

    @Query("select t.created_at from ChangePasswordPasscodeEntity t where t.email = :email")
    Instant findCreatedAtByEmail(@Param("email") String email);

    ChangePasswordPasscodeEntity save(ChangePasswordPasscodeEntity passcodeEntity);

    @Modifying
    @Query("update ChangePasswordPasscodeEntity t set t.passcode = :passcode, t.created_at = :created_at where t.email = :email")
    void updatePasscodeByEmail(@Param("email") String email, @Param("passcode") int passcode, @Param("created_at") Instant created_at);
}
