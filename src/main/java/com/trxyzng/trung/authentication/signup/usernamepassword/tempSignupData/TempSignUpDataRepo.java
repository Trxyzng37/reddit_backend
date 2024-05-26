package com.trxyzng.trung.authentication.signup.usernamepassword.tempSignupData;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface TempSignUpDataRepo extends JpaRepository<TempSignUpDataEntity, String> {

    Optional<TempSignUpDataEntity> findByEmail(String email);
    TempSignUpDataEntity save(TempSignUpDataEntity tempSignUpDataEntity);

    void deleteTempSignUpDataEntityByEmail(String email);

    void deleteTempSignUpDataEntityByUsername(String username);

    @Query("select case when count(t) > 0 then 1 else 0 end from TempSignUpDataEntity t where t.email = :email")
    int isDataByEmailExist(@Param("email") String email);
    @Query("select case when count(t) > 0 then 1 else 0 end from TempSignUpDataEntity t where t.username = :username")
    int isDataByUsernameExist(@Param("username") String username);
}
