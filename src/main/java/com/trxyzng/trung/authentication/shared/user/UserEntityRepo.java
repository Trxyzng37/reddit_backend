package com.trxyzng.trung.authentication.shared.user;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UserEntityRepo extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);

    @Query("select t.uid from UserEntity t where t.email = :email")
    int findUidByEmail(String email);

    @Query("select t.password from UserEntity t where t.email = :email")
    String findPasswordByEmail(String email);

    @Query("select t.uid from UserEntity t where t.username = :username")
    int findUidByUsername(@Param("username") String username);

    @Query("select t.username from UserEntity t where t.uid = :uid")
    String findUsernameByUid(@Param("uid") int uid);

    UserEntity save(UserEntity userEntity);

    @Query("select case when count(t) > 0 then 1 else 0 end from UserEntity t where  t.username like :username")
    int isUserEntityByUsernameExist(@Param("username") String username);

    @Query("select case when count(t) > 0 then 1 else 0 end from UserEntity t where t.email = :email")
    int isUserEntityByEmailExist(@Param("email") String email);

    @Query("select case when count(t) > 0 then 1 else 0 end from UserEntity t where t.uid = :uid")
    int isUserEntityByUidExist(@Param("uid") int uid);

    @Query("select case when count(t) > 0 then 1 else 0 end from UserEntity t where t.email = :email")
    int existByEmail(String email);

    @Query("select case when count(t) > 0 then 1 else 0 end from UserEntity t where t.username = :username")
    int existByUsername(String username);

    @Modifying
    @Query("update UserEntity t set t.username = :username where t.email = :email")
    void UpdateUsernameByEmail(@Param("email") String email, @Param("username") String username);
}
