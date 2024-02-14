package com.trxyzng.trung.authentication.changepassword.change_password;

import com.trxyzng.trung.user.shared.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface ChangePasswordRepo extends JpaRepository<UserEntity, Integer> {
    @Modifying
    @Query("update UserEntity t set t.password = :newPassword where t.email = :email")
    void updatePasswordForEmail(@Param("email") String email, @Param("newPassword") String newPassword);
    @Query("select t.password from UserEntity t where t.email = :email")
    Optional<String> SelectPasswordByEmail(@Param("email") String email);
}
