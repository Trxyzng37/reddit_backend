package com.trxyzng.trung.authentication.shared.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepo extends JpaRepository<UserEntity, Integer> {
//    @Query("select uid, username, password, email from User.users where username = :name")
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);

//    @Query("select 1 from UserEntity t where t.email = :email")
//    boolean isEMailExistInDB(@Param("email") String email);
    UserEntity save(UserEntity userEntity);
}
