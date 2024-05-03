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

    @Query("select t.uid from UserEntity t where t.username = :username")
    Optional<Integer> findUidByUsername(@Param("username") String username);
    UserEntity save(UserEntity userEntity);

    @Query("select case when count(t) > 0 then 1 else 0 end from UserEntity t where t.username = :username")
    int isUserEntityByUsernameExist(@Param("username") String username);

    @Query("select case when count(t) > 0 then 1 else 0 end from UserEntity t where t.email = :email")
    int isUserEntityByEmailExist(@Param("email") String email);
}
